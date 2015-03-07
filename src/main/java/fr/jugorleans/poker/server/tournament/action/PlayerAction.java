package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Created by francoispenaud on 07/03/15.
 */
public interface PlayerAction {

    void setPlay(Play play);

    Play action(Player player, int bet);
}
