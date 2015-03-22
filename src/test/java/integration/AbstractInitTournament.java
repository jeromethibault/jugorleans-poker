package integration;

import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Pot;
import fr.jugorleans.poker.server.core.play.Round;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import fr.jugorleans.poker.server.service.TournamentService;
import fr.jugorleans.poker.server.tournament.Play;
import fr.jugorleans.poker.server.tournament.Tournament;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe abstraite permettant d'initialiser un tournoi 4 joueurs
 */
public abstract class AbstractInitTournament {

    public static final int INITIAL_STACK = 10000;

    @Autowired
    protected DefaultStrongestHandResolver defaultStrongestHandResolver;

    @Autowired
    protected TournamentService tournamentService;

    protected Tournament wsop;
    protected Pot pot;
    protected Player jerome;
    protected Player francois;
    protected Player nicolas;
    protected Player julien;

    @Before
    public void initAndStartTournament() {
        jerome = Player.builder().nickName("Jer").build();
        francois = Player.builder().nickName("Fra").build();
        nicolas = Player.builder().nickName("Nic").build();
        julien = Player.builder().nickName("Jul").build();


        wsop = tournamentService.createNewTournament();

        tournamentService.addPlayer(wsop.getId(), jerome);
        tournamentService.addPlayer(wsop.getId(), francois);
        tournamentService.addPlayer(wsop.getId(), nicolas);
        tournamentService.addPlayer(wsop.getId(), julien);

        Assert.assertEquals("Nombre de joueurs inscrits KO", 4, wsop.nbPlayersIn());
        Assert.assertNull("Aucune main jouée", wsop.getTable1().getCurrentPlay());

        // Check init des stacks + sieges
        wsop.getPlayers().stream().forEach(p -> {
            Assert.assertTrue(p.getStack() == INITIAL_STACK);
            Assert.assertTrue(p.getSeat().getNumber() > 0);
        });

        // On remplace les sièges tirés aléatoirement pour maitriser les données du test
        jerome.getSeat().setNumber(1);
        francois.getSeat().setNumber(2);
        nicolas.getSeat().setNumber(3);
        julien.getSeat().setNumber(4);
        wsop.getTable1().setSeatPlayDealer(4);
    }


    @After
    public void checkCumulStacks() {
        Assert.assertEquals("Somme des stacks", 4 * INITIAL_STACK,
                wsop.getPlayers().stream().mapToInt(p -> p.getStack()).sum());
    }

    protected Play newPlay() {
        Play play = wsop.newPlay();
        play.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        pot = play.getPot();
        return play;
    }

    protected void checkPlayerState(Player player, Integer stackExpected, Action lastActionExpected, int potAmountExpected) {
        if (stackExpected != null) {
            Assert.assertEquals("Stack restant du joueur ", stackExpected, player.getStack());
        }
        Assert.assertEquals("Last Action du joueur ", lastActionExpected, player.getLastAction());
        Assert.assertEquals("Montant du pot", potAmountExpected, pot.getAmount());
    }

    protected void checkPlayerState(Player player, Action lastActionExpected, int potAmountExpected) {
        checkPlayerState(player, null, lastActionExpected, potAmountExpected);
    }

    protected void checkPlayOver(Play play) {
        Assert.assertEquals("Round courant KO", Round.SHOWDOWN, play.getCurrentRound());
        Assert.assertNotNull("Main terminée", play.getPots());
    }
}
