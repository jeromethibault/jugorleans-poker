package integration;

import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Round;
import fr.jugorleans.poker.server.tournament.Play;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test d'intégration
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
@Slf4j
public class AutoCheckFoldTimerTest extends AbstractInitTournament {


    /**
     * Test de vérification des FOLD automatiques en cas de délai dépassé (et impossibilité de checker)
     */
    @Test
    public void testAutoFold() {
        Play play = newPlay();

        // Julien UTG doit commencer, François SB, Nicolas BB
        play.action(julien, Action.ALL_IN, 7);
        checkPlayerState(julien, 0, Action.ALL_IN, 10030);

        // Vérification temps écoulé avec fold de tous les autres joueurs sans action
        try {
            Thread.sleep(4200);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull("Main terminée", play.getWinners());
        Assert.assertEquals("Vainqueur erroné", julien, play.getWinners().stream().findFirst().get());
        checkPlayerState(julien, 10030, Action.ALL_IN, 10030);
        checkCumulStacks(play);
    }

    /**
     * Test de vérification des CHECK automatiques en cas de délai dépassé (et possibilité de checker)
     */
    @Test
    public void testAutoCheck() {
        Play play = newPlay();
        // Julien UTG doit commencer, François SB, Nicolas BB

        play.action(julien, Action.FOLD, 0);
        play.action(jerome, Action.FOLD, 0);
        play.action(francois, Action.CALL, 0);
        play.action(nicolas, Action.CHECK, 0);
        checkPlayerState(jerome, INITIAL_STACK, Action.FOLD, 40);
        checkPlayerState(francois, 9980, Action.NONE, 40);
        Assert.assertEquals("Round courant KO", Round.FLOP, play.getCurrentRound());

        play.action(francois, Action.CHECK, 0);
        play.action(nicolas, Action.CHECK, 0);
        Assert.assertEquals("Round courant KO", Round.TURN, play.getCurrentRound());
        play.action(francois, Action.CHECK, 0);
        play.action(nicolas, Action.CHECK, 0);
        Assert.assertEquals("Round courant KO", Round.RIVER, play.getCurrentRound());

        // Vérification temps écoulé avec check auto des 2 joueurs restants sur river
        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals("Round courant KO", Round.SHOWDOWN, play.getCurrentRound());
        Assert.assertNotNull("Main terminée", play.getWinners());
        checkCumulStacks(play);
    }
}
