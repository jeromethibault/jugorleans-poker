package integration;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.core.play.Round;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import fr.jugorleans.poker.server.tournament.Play;
import fr.jugorleans.poker.server.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test d'intégration
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
@Slf4j
public class NewTournamentTest {

    public static final int INITIAL_STACK = 10000;


    @Autowired
    private DefaultStrongestHandResolver defaultStrongestHandResolver;

    private Pot pot;

    @Test
    public void newTournament() {

        Player jerome = Player.builder().nickName("Jer").build();
        Player francois = Player.builder().nickName("Fra").build();
        Player nicolas = Player.builder().nickName("Nic").build();
        Player julien = Player.builder().nickName("Jul").build();

        Tournament wsop = Tournament.builder()
                .initialStack(INITIAL_STACK)
                .nbMaxPlayers(10)
                .players(Lists.newArrayList(jerome, francois, nicolas, julien))
                .lastPlays(Lists.newArrayList())
                .build();

        wsop.start();

        Assert.assertEquals("Nombre de joueurs inscrits KO", 4, wsop.nbPlayersIn());
        Assert.assertNull("Aucune main jouée", wsop.getCurrentPlay());

        // Check init des stacks + sieges
        wsop.getPlayers().stream().forEach(p -> {
            Assert.assertTrue(p.getStack() == INITIAL_STACK);
            Assert.assertTrue(p.getSeat().getNumber() > 0);
        });

        // on remplace les sièges tirés aléatoirement pour maitriser les données du test
        jerome.getSeat().setNumber(1);
        francois.getSeat().setNumber(2);
        nicolas.getSeat().setNumber(3);
        julien.getSeat().setNumber(4);
        wsop.setSeatPlayDealer(4);

        /**
         * New Play
         */
        Play play = wsop.newPlay();
        play.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        pot = play.getPot();
        Assert.assertEquals("Nombre cartes restantes KO", 44, play.getDeck().cardsLeft());
        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getRound());


        // Check init des Action
        wsop.getPlayers().stream().forEach(p -> {
            Assert.assertEquals(Action.NONE, p.getLastAction());
        });

        Assert.assertNotNull("Main en cours", wsop.getCurrentPlay());
        Player player = play.whoMustPlay();
        // Julien UTG doit commencer
        Assert.assertEquals(julien, player);

        play.action(julien, Action.BET, 150);
        checkPlayerState(julien, 9850, Action.BET, 150);

        try {
            play.action(julien, Action.BET, 150);
            Assert.fail("Pas au tour du joueur ");
        } catch (IllegalStateException e) {
        }

        play.action(jerome, Action.BET, 350);
        checkPlayerState(jerome, 9650, Action.BET, 500);

        play.action(francois, Action.CALL, 350);
        checkPlayerState(francois, 9650, Action.CALL, 850);

        try {
            play.action(julien, Action.FOLD, 0);
            Assert.fail("Pas au tour du joueur");
        } catch (IllegalStateException e) {
        }
        play.action(nicolas, Action.FOLD, 0);
        checkPlayerState(nicolas, INITIAL_STACK, Action.FOLD, 850);
        Assert.assertEquals("Nb cards sur le board - preflop ", 0, play.getBoard().nbCard());

        play.action(julien, Action.CALL, 200);
        // LastAction Action.NONE car nouveau round
        checkPlayerState(julien, 9650, Action.NONE, 1050);
        Assert.assertEquals("Round courant KO", Round.FLOP, play.getRound());
        Assert.assertEquals("Nb cards sur le board - flop ", 3, play.getBoard().nbCard());

        // Au tour de Fra (car nouveau round après le dealer)
        play.action(francois, Action.CHECK, 0);
        checkPlayerState(francois, 9650, Action.CHECK, 1050);

        play.action(julien, Action.BET, 500);
        checkPlayerState(julien, 9150, Action.BET, 1550);

        // Mauvaise relance
        play.action(jerome, Action.BET, 999);
        checkPlayerState(jerome, 9150, Action.CALL, 2050);

        // Check interdit -> call; Action.NONE car nouveau round (turn)
        play.action(francois, Action.CHECK, 0);
        checkPlayerState(jerome, 9150, Action.NONE, 2550);
        Assert.assertEquals("Round courant KO", Round.TURN, play.getRound());
        Assert.assertEquals("Nb cards sur le board - turn ", 4, play.getBoard().nbCard());


        // Au tour de Fra (car nouveau round après le dealer)
        play.action(francois, Action.CHECK, 0);
        checkPlayerState(francois, 9150, Action.CHECK, 2550);

        // Call suite à un check -> check
        play.action(julien, Action.CALL, 700);
        checkPlayerState(julien, 9150, Action.CHECK, 2550);

        // TODO A reprendre avec ajout gestion des blinds - mise de 1 < BB - sera un call donc un check ici
        play.action(jerome, Action.BET, 1);
        checkPlayerState(jerome, 9149, Action.BET, 2551);

        play.action(francois, Action.BET, 1000);
        checkPlayerState(francois, 8150, Action.BET, 3551);

        play.action(julien, Action.CALL, 1000);
        checkPlayerState(julien, 8150, Action.CALL, 4551);

        // Call pourr voir la river
        play.action(jerome, Action.CALL, 1);
        checkPlayerState(jerome, 8150, Action.NONE, 5550);

        Assert.assertEquals("Round courant KO", Round.RIVER, play.getRound());
        Assert.assertEquals("Nb cards sur le board - river ", 5, play.getBoard().nbCard());

        // Au tour de Fra (car nouveau round après le dealer) - 3 checks
        play.action(francois, Action.CHECK, 0);
        play.action(julien, Action.CHECK, 0);
        play.action(jerome, Action.CHECK, 0);
        Assert.assertEquals("Montant du pot final", 5550, pot.getAmount().intValue());

        Assert.assertEquals("Round courant KO", Round.SHOWDOWN, play.getRound());
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
        play = wsop.newPlay();
        pot = play.getPot();
        Assert.assertEquals("Round courant KO", Round.PREFLOP, play.getRound());
        Assert.assertEquals("Nb cards sur le board - preflop ", 0, play.getBoard().nbCard());

        int stackNicolas = nicolas.getStack();
        // Dealer passe de Jérôme à François, SB Nicolas, BB Julien, Jérôme UTG
        play.action(jerome, Action.FOLD, 50);

        play.action(francois, Action.BET, 200);
        checkPlayerState(francois, Action.BET, 200);

        play.action(nicolas, Action.BET, 400);
        checkPlayerState(nicolas, Action.BET, 600);

        play.action(julien, Action.FOLD,0);

        play.action(francois, Action.FOLD, 200);
        checkPlayerState(francois, Action.FOLD, 600);

        Assert.assertEquals("Stack Nicolas apres gain", stackNicolas + 200, nicolas.getStack().intValue());

        Assert.assertEquals("Round courant KO", Round.SHOWDOWN, play.getRound());
        Assert.assertEquals("Nb cards sur le board - showdown preflop ", 0, play.getBoard().nbCard());

        checkCumulStacks(play);


    }

    private void checkCumulStacks(Play play) {
        Assert.assertEquals("Somme des stacks", 4 * INITIAL_STACK,
                play.getPlayers().keySet().stream().mapToInt(p -> p.getStack()).sum());
    }

    public void checkPlayerState(Player player, Integer stackExpected, Action lastActionExpected, int potAmountExpected) {
        if (stackExpected != null) {
            Assert.assertEquals("Stack restant du joueur ", stackExpected, player.getStack());
        }
        Assert.assertEquals("Last Action du joueur ", lastActionExpected, player.getLastAction());
        Assert.assertEquals("Montant du pot", potAmountExpected, pot.getAmount().intValue());
    }

    public void checkPlayerState(Player player, Action lastActionExpected, int potAmountExpected) {
        checkPlayerState(player, null, lastActionExpected, potAmountExpected);
    }
}
