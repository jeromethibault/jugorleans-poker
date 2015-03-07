package fr.jugorleans.poker.server.tournament.action;

import com.google.common.base.Preconditions;
import fr.jugorleans.poker.server.core.play.Player;
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
    public Play action(Player player, int bet) {

        Preconditions.checkState(bet >= play.getLastRaise(), "Fausse relance");


        play.getPot().addToPot(bet);
        player.bet(bet);
        play.getPlayers().merge(player, bet, (v1, v2) -> v1 + v2);

        return this.play;
    }
}
