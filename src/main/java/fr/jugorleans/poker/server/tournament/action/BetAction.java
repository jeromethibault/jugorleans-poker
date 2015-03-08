package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un bet
 */
public class BetAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) throws MustCallException {

        // TODO gérer stack insuffisant / auto allin

        Pot pot = play.getPot();

        // Récupération montant dernière relance
        int lastRaise = pot.getLastRaise();

        // Montant sur lequel le joueur a besoin de s'aligner pour continuer sur le round (plus grande mise cumulée d'un joueur en jeu)
        int roundBet = pot.getRoundBet();

        // Récupération mises engagées par le joueur sur le round
        Integer roundPlayerBet = play.getPlayers().get(player);

        boolean validBet = (bet + roundPlayerBet == roundBet) ||
                ((bet + roundPlayerBet > roundBet) && (bet >= 2 * lastRaise));

        // Si mise invalide => c'est un call
        if (!validBet) {
            throw new MustCallException();
        }

        // MAJ pot
        pot.addToPot(bet);
        player.bet(bet);
        play.updatePlayerPlayAmount(player, bet);

        // MAJ montant investi par le joueur
        pot.setLastRaise(Math.max(lastRaise, bet - roundBet));
        roundPlayerBet = play.getPlayers().get(player);
        pot.setRoundBet(roundPlayerBet);

        return this.play;
    }
}
