package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * Timer gérant le check/fold automatique si le délai de jeu d'un joueur est dépassé
 */
@Getter
@Builder
@Slf4j
public class TimerTaskAction extends TimerTask {

    /**
     * Play associé à la tâche
     */
    private Play play;

    @Override
    public void run() {
        Player player = play.whoMustPlay();
        log.info("Délai écoulé pour " + player.getNickName());

        boolean checkPossible = play.getPot().getRoundBet() == 0;
        if (checkPossible){
            play.action(player, Action.CHECK, 0);
        } else {
            // Autofold
            play.action(player, Action.FOLD, 0);
        }
    }
}
