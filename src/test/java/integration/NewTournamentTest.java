package integration;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Play;
import fr.jugorleans.poker.server.core.tournament.Player;
import fr.jugorleans.poker.server.core.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test d'intégration
 */
@Slf4j
public class NewTournamentTest {

    @Test
    public void newTournament() {


        Player jerome = Player.builder().nickName("Jer").build();
        Player francois = Player.builder().nickName("Fra").build();
        Player nicolas = Player.builder().nickName("Nic").build();

        Tournament wsop = Tournament.builder()
                .initialStack(1000)
                .nbMaxPlayers(10)
                .players(Lists.newArrayList(jerome, francois, nicolas))
                .lastPlays(Lists.newArrayList())
                .build();

        wsop.start();

        Assert.assertEquals("Nombre de joueurs inscrits KO", 3, wsop.nbPlayersIn());
        Assert.assertNull("Aucune main jouée", wsop.getCurrentPlay());
        Play play = wsop.newPlay();
        Assert.assertNotNull("Main en cours", wsop.getCurrentPlay());

        wsop.getPlayers().stream().forEach(p -> {
            Assert.assertTrue(p.getStack() > 0);
            Assert.assertTrue(p.getSeat().getNumber() > 0);
        });


    }
}
