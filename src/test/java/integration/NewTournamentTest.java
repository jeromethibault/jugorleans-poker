package integration;

import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Player;
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
public class NewTournamentTest extends AbstractInitTournament {

    @Test
    public void testTournamentWithFoldCallCheckBet() {

        /**
         * New Play
         */
        Play play = wsop.newPlay();
        play.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        pot = play.getPot();
        Assert.assertEquals("Nombre cartes restantes KO", 44, play.getDeck().cardsLeft());
        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getCurrentRound());


        // Check init des Action
        wsop.getPlayers().stream().forEach(p -> {
            Assert.assertEquals(Action.NONE, p.getLastAction());
        });

        Assert.assertNotNull("Main en cours", wsop.getTable1().getCurrentPlay());
        Player player = play.whoMustPlay();
        // Julien UTG doit commencer, François SB, Nicolas BB
        Assert.assertEquals(julien, player);

        play.action(julien, Action.BET, 150);
        checkPlayerState(julien, 9850, Action.BET, 180);

        try {
            play.action(julien, Action.BET, 150);
            Assert.fail("Pas au tour du joueur ");
        } catch (IllegalStateException e) {
        }

        play.action(jerome, Action.BET, 350);
        checkPlayerState(jerome, 9650, Action.BET, 530);

        play.action(francois, Action.CALL, 5);
        checkPlayerState(francois, 9650, Action.CALL, 870);

        try {
            play.action(julien, Action.FOLD, 0);
            Assert.fail("Pas au tour du joueur");
        } catch (IllegalStateException e) {
        }
        play.action(nicolas, Action.FOLD, 0);
        checkPlayerState(nicolas, 9980, Action.FOLD, 870);
        Assert.assertEquals("Nb cards sur le board - preflop ", 0, play.getBoard().nbCard());

        play.action(julien, Action.CALL, 2000);
        // LastAction Action.NONE car nouveau currentRound
        checkPlayerState(julien, 9650, Action.NONE, 1070);
        Assert.assertEquals("Round courant KO", Round.FLOP, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - flop ", 3, play.getBoard().nbCard());

        // Au tour de Fra (car nouveau currentRound après le dealer)
        play.action(francois, Action.CHECK, 1);
        checkPlayerState(francois, 9650, Action.CHECK, 1070);

        play.action(julien, Action.BET, 500);
        checkPlayerState(julien, 9150, Action.BET, 1570);

        // Mauvaise relance
        play.action(jerome, Action.BET, 999);
        checkPlayerState(jerome, 9150, Action.CALL, 2070);

        // Check interdit -> call; Action.NONE car nouveau currentRound (turn)
        play.action(francois, Action.CHECK, 1);
        checkPlayerState(jerome, 9150, Action.NONE, 2570);
        Assert.assertEquals("Round courant KO", Round.TURN, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - turn ", 4, play.getBoard().nbCard());


        // Au tour de Fra (car nouveau currentRound après le dealer)
        play.action(francois, Action.CHECK, 3);
        checkPlayerState(francois, 9150, Action.CHECK, 2570);

        play.action(julien, Action.BET, 1000);
        checkPlayerState(julien, 8150, Action.BET, 3570);

        play.action(jerome, Action.CALL, 6);
        checkPlayerState(jerome, 8150, Action.CALL, 4570);

        // Fausse relance -> Call
        play.action(francois, Action.BET, 1999);
        checkPlayerState(francois, 8150, Action.NONE, 5570);

        Assert.assertEquals("Round courant KO", Round.RIVER, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - river ", 5, play.getBoard().nbCard());

        // Au tour de Fra (car nouveau currentRound après le dealer) - 3 checks
        play.action(francois, Action.CHECK, 33);
        // Call suite à un check -> check
        play.action(julien, Action.CALL, 8);
        // Bet < big blind => call
        play.action(jerome, Action.CALL, 15);
        Assert.assertEquals("Montant du pot final", 5570, pot.getAmount().intValue());

        Assert.assertEquals("Round courant KO", Round.SHOWDOWN, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - showdown ", 5, play.getBoard().nbCard());

        checkCumulStacks(play);

        try {
            play.action(julien, Action.BET, 0);
            Assert.fail("Play terminé");
        } catch (IllegalStateException e) {
        }

        /**
         * New Play
         */
        int stackNicolasAfterFirstPlay = nicolas.getStack();
        play = wsop.newPlay();
        pot = play.getPot();
        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - preflop ", 0, play.getBoard().nbCard());

        // Dealer passe de Jérôme à François, SB Nicolas, BB Julien, Jérôme UTG
        play.action(jerome, Action.FOLD, 50);

        play.action(francois, Action.BET, 200);
        checkPlayerState(francois, Action.BET, 230);

        play.action(nicolas, Action.BET, 400);
        checkPlayerState(nicolas, Action.BET, 630);

        play.action(julien, Action.FOLD, 0);

        play.action(francois, Action.FOLD, 200);
        checkPlayerState(francois, Action.FOLD, 630);

        // +220 (20 BB + 200 François) - pas de réel showdown
        Assert.assertEquals("Stack Nicolas apres gain", stackNicolasAfterFirstPlay + 220, nicolas.getStack().intValue());

        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getCurrentRound());
        Assert.assertEquals("Nb cards sur le board - showdown preflop ", 0, play.getBoard().nbCard());
        Assert.assertNotNull("Main terminée", play.getWinners());

        checkCumulStacks(play);

    }


    @Test
    public void testTournamentWithAllInsPreflop() {

        Play play = wsop.newPlay();
        play.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        pot = play.getPot();

        // Julien UTG doit commencer, François SB, Nicolas BB

        play.action(julien, Action.ALL_IN, 7);
        checkPlayerState(julien, 0, Action.ALL_IN, 10030);

        play.action(jerome, Action.FOLD, 8);
        checkPlayerState(jerome, INITIAL_STACK, Action.FOLD, 10030);

        play.action(francois, Action.FOLD, 3);
        checkPlayerState(francois, INITIAL_STACK - 10, Action.FOLD, 10030);

        play.action(nicolas, Action.CALL, 66);
        checkPlayerState(nicolas, Action.ALL_IN, 20010);

        Assert.assertNotNull("Main terminée", play.getWinners());
        Assert.assertEquals("Nombre de joueurs restants", 3, wsop.nbRemainingPlayers());
        checkCumulStacks(play);

        play = wsop.newPlay();
        play.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        Assert.assertEquals("Nombre de joueurs en jeu sur la main ", 3, play.getPlayers().size());

        // Check init des actions
        wsop.getPlayers().stream().filter(p -> !p.isOut()).forEach(p -> {
            Assert.assertEquals(Action.NONE, p.getLastAction());
            Assert.assertFalse(p.isAllIn());
            Assert.assertFalse(p.isFolded());
        });

        for (int i = 0; i < 3; i++) {
            play.action(play.whoMustPlay(), Action.ALL_IN, 3);
        }
        Assert.assertNotNull("Main terminée", play.getWinners());

        checkCumulStacks(play);

        // Vérification temps écoulé
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long nbSec = wsop.getClock().getElapsedTime();
        Assert.assertTrue("Temps écoulé : " + nbSec, nbSec >= 1 && nbSec < 5);
    }
}
