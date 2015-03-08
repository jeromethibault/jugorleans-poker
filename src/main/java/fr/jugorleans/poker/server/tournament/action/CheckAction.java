package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un check
 */
public class CheckAction implements PlayerAction {

    private Play play;

    @Override
    public void setPlay(Play play) {
        this.play = play;
    }

    @Override
    public Play action(Player player, int bet) throws MustCallException {

        // Check possible si aucune mise n'a été effectuée sur le currentRound, sinon c'est un call
        boolean actionAutorisee = play.getPot().getRoundBet() == 0;
        if (!actionAutorisee) {
            throw new MustCallException();
        }
        player.check();

        return this.play;
    }
}
