package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Created by francoispenaud on 07/03/15.
 */
public class BetAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) {
        play.getPot().addToPot(bet);
        player.bet(bet);
        play.getPlayers().merge(player, bet, (v1, v2) -> v1 + v2);

        return this.play;
    }
}
