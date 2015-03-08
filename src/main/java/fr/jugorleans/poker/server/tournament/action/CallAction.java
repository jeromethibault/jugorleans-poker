package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un call
 */
public class CallAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) {

        // TODO gérer stack insuffisant / auto allin

        Pot pot = play.getPot();

        // Montant sur lequel le joueur a besoin de s'aligner pour continuer sur le currentRound (plus grande mise cumulée d'un joueur en jeu)
        int roundBet = pot.getRoundBet();

        // Récupération mises engagées par le joueur sur le currentRound
        Integer roundPlayerBet = play.getPlayers().get(player);

        // Calcul du call
        int callValue = roundBet - roundPlayerBet;
        player.call(callValue);

        // MAJ pot + montant investi par le joueur
        pot.addToPot(callValue);
        play.updatePlayerPlayAmount(player, callValue);


        return this.play;
    }
}
