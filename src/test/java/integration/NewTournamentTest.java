package integration;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.tournament.Play;
import fr.jugorleans.poker.server.core.play.Round;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test d'intégration
 */
@Slf4j
public class NewTournamentTest {

    public static final int INITIAL_STACK = 10000;

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
        wsop.setSeatPlayDealer(1);

        Play play = wsop.newPlay();
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
        Assert.assertEquals("Stack restant du joueur " , 9850, julien.getStack().intValue());
        Assert.assertEquals("Last Action du joueur " , Action.BET, julien.getLastAction());
        Assert.assertEquals("Montant du pot", 150, play.getPot().getAmount().intValue());

        try {
            play.action(julien, Action.BET, 150);
            Assert.fail("Pas au tour du joueur " );
        } catch (RuntimeException e) {
        }

        play.action(jerome, Action.BET, 350);
        Assert.assertEquals("Stack restant du joueur " , 9650, jerome.getStack().intValue());
        Assert.assertEquals("Last Action du joueur " , Action.BET, jerome.getLastAction());
        Assert.assertEquals("Montant du pot", 500, play.getPot().getAmount().intValue());

        play.action(francois, Action.BET, 350);
        Assert.assertEquals("Stack restant du joueur " , 9650, francois.getStack().intValue());
        Assert.assertEquals("Last Action du joueur " , Action.BET, francois.getLastAction());
        Assert.assertEquals("Montant du pot", 850, play.getPot().getAmount().intValue());

        try {
            play.action(julien, Action.FOLD, 0);
            Assert.fail("Pas au tour du joueur " );
        } catch (RuntimeException e) {
        }
        play.action(nicolas, Action.FOLD, 0);
        Assert.assertEquals("Last Action du joueur " , Action.FOLD, nicolas.getLastAction());
        Assert.assertEquals("Stack restant du joueur " , INITIAL_STACK, nicolas.getStack().intValue());

        Assert.assertEquals("Nb cards sur le board - preflop ", 0, play.getBoard().nbCard());

        play.action(julien,Action.BET, 200);
        Assert.assertEquals("Round courant KO", Round.FLOP, play.getRound());
        Assert.assertEquals("Stack restant du joueur " , 9650, julien.getStack().intValue());
        // LastAction NONE car nouveau round
        Assert.assertEquals("Last Action du joueur " , Action.NONE, julien.getLastAction());
        Assert.assertEquals("Montant du pot", 1050, play.getPot().getAmount().intValue());


        Assert.assertEquals("Nb cards sur le board - flop ", 3, play.getBoard().nbCard());

        // Au tour de Fra (car nouveau round après le dealer)
        play.action(francois, Action.CHECK, 0);
        Assert.assertEquals("Stack restant du joueur " , 9650, francois.getStack().intValue());
        Assert.assertEquals("Last Action du joueur " , Action.CHECK, francois.getLastAction());
        Assert.assertEquals("Montant du pot", 1050, play.getPot().getAmount().intValue());

        play.action(julien,Action.BET, 500);
        Assert.assertEquals("Stack restant du joueur ", 9150, julien.getStack().intValue());
        Assert.assertEquals("Last Action du joueur ", Action.BET, julien.getLastAction());
        Assert.assertEquals("Montant du pot", 1550, play.getPot().getAmount().intValue());


    }
}
