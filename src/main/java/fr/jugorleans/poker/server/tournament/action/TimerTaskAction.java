package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.tournament.Play;
import lombok.Builder;
import lombok.Getter;

import java.util.TimerTask;

/**
 * Timer gérant le check/fold automatique si le délai de jeu d'un joueur est dépassé
 */
@Getter
@Builder
public class TimerTaskAction extends TimerTask {

    /**
     * Play associé à la tâche
     */
    private Play play;

    @Override
    public void run() {
        boolean checkPossible = play.getPot().getRoundBet() == 0;
        if (checkPossible){
            play.action(play.whoMustPlay(), Action.CHECK, 0);
        } else {
            // Autofold
            play.action(play.whoMustPlay(), Action.FOLD, 0);
        }

    }
}
