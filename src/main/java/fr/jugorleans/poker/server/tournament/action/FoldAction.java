package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Created by francoispenaud on 07/03/15.
 */
public class FoldAction implements PlayerAction {

    private Play play;

    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) {
        player.fold();

        return this.play;
    }
}
