package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un fold
 */
public class FoldAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) {
        player.fold();

        return this.play;
    }
}
