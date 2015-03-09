package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un bet
 */
public class BetAction extends CommonAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) throws MustCallException {

        // Vérification que le joueur ne se retrouve pas allin - si c'est le cas la mise est réduite à la hauteur du tapis
        int stack = checkIsAllIn(player, bet);

        Pot pot = play.getPot();

        // Récupération montant dernière relance
        int lastRaise = pot.getLastRaise();

        // Montant sur lequel le joueur a besoin de s'aligner pour continuer sur le round (plus grande mise cumulée d'un joueur en jeu)
        int roundBet = pot.getRoundBet();

        // Récupération mises engagées par le joueur sur le round
        Integer roundPlayerBet = play.getPlayers().get(player);

        boolean validBet = (bet + roundPlayerBet == roundBet) ||
                ((bet + roundPlayerBet > roundBet) && (bet >= 2 * lastRaise)) || player.isAllIn();

        // Si mise invalide => c'est un call
        if (!validBet) {
            throw new MustCallException();
        }

        // la mise réelle est le minimum entre la mise souhaitée et le stack du joueur
        bet = Math.min(bet, stack);

        // MAJ pot
        pot.addToPot(bet);

        // Prise en compte du bet au niveau du joueur
        player.bet(bet);
        // MAJ montant investi par le joueur
        play.updatePlayerPlayAmount(player, bet);

        // MAJ lastRaise et RoundBet du pot
        pot.setLastRaise(Math.max(lastRaise, bet - roundBet));
        pot.setRoundBet(Math.max(roundBet, play.getPlayers().get(player)));

        return this.play;
    }
}
