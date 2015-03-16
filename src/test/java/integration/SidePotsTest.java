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
public class SidePotsTest extends AbstractInitTournament {


    /**
     * Test de vérification de la gestion des sides Pot
     */
    @Test
    public void testSidePots() {
        Play play = newPlay();
        // Julien UTG doit commencer, François SB, Nicolas BB

        play.action(julien, Action.BET, 500);
        play.action(jerome, Action.BET, 1000);
        play.action(francois, Action.BET, 1500); // TODO différencier action bet et raise ? car mise de 1510 avec blinds là donc relance à 1510
        play.action(nicolas, Action.BET, 2000);
        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getCurrentRound());

        play.action(julien, Action.FOLD, 0);
        play.action(jerome, Action.FOLD, 0);
        play.action(francois, Action.FOLD, 0);
        checkPlayerState(nicolas, 13010, Action.BET, 5030);

        // Stack Nicolas 13K, Julien 9.5K, Jerome 9K, François 8.5K
        play = newPlay();
        // Jérôme UTG doit commencer, Nicolas SB, Julien BB
        play.action(jerome, Action.ALL_IN, 0);
        play.action(francois, Action.ALL_IN, 0);
        play.action(nicolas, Action.CALL, 0);
        play.action(julien, Action.CALL, 0);

        Assert.assertEquals("Round courant KO", Round.FLOP, play.getCurrentRound());

        play.action(nicolas, Action.ALL_IN, 0);
        play.action(julien, Action.CALL, 0);

        checkPlayOver(play);
    }
}
