package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Action associée à un ALL IN => = bet du stack restant
 */
public class AllInAction extends BetAction {

    @Override
    public Play action(Player player, int bet) throws MustCallException {
        int stack = player.getStack();
        return super.action(player, stack);
    }
}
