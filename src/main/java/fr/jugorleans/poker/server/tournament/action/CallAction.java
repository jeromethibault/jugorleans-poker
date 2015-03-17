package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un call
 */
public class CallAction extends CommonAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) {

        Pot pot = play.getPot();
        // Montant sur lequel le joueur a besoin de s'aligner pour continuer sur le round (plus grande mise cumulée d'un joueur en jeu)
        bet = pot.getRoundBet();

        // Vérification que le joueur ne se retrouve pas allin
        int stack = checkIsAllIn(player, bet);

        // Récupération mises engagées par le joueur sur le round
        Integer roundPlayerBet = play.getPlayers().get(player).getCurrentRound();

        // Calcul du call : min entre la mise demandée - montant déjà investi, et le stack du joueur
        int callValue = Math.min(bet - roundPlayerBet, stack);
        player.call(callValue);

        // MAJ pot + montant investi par le joueur
        pot.addToPot(callValue);
        play.updatePlayerPlayAmount(player, callValue);

        return this.play;
    }
}
