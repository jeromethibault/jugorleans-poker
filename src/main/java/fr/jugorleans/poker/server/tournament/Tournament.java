package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Structure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Tournoi
 */
@Getter
@Setter
@Builder
@ToString

/**
 * Tournoi
 * TODO gérer id de tournoi et propager jusqu'au Play
 */
public class Tournament {

    /**
     * Joueurs inscrits
     */
    private List<Player> players;

    /**
     * Montant du stack initial de chaque joueur
     */
    private int initialStack = 1000;

    /**
     * Nombre maximum de joueurs
     */
    private int nbMaxPlayers = 10;

    /**
     * Vainqueur
     */
    private Player winner;

    /**
     * Liste des mains jouées
     */
    private List<Play> lastPlays;


    /**
     * Flag de lancement du tournoi
     */
    private boolean started = false;

    /**
     * Table se déroule le tournoi
     */
    private Table table;

    /**
     * Structure du tournoi
     */
    private Structure structure;

    /**
     * Round (blinds) courant
     */
    private Integer currentBlindRound;

    /**
     * Horloge du tournoi
     */
    private GameClock clock;

    /**
     * Timer de gestion automatiquement de changement de rounds
     */
    private Timer timer;

    /**
     * Horloge du round
     *//*
    private Clock round; //voir si pertinent
    */

    /**
     * Ajout de joueurs
     *
     * @param playersToAdd joueurs à ajouter
     */
    public void addPlayers(Player... playersToAdd) {
        if (players == null) {
            players = Lists.newArrayList();
        }
        players.addAll(Arrays.stream(playersToAdd).collect(Collectors.toList()));
    }

    /**
     * Démarrage du tournoi
     */
    public void start(Structure structure) {
        Preconditions.checkState(!started, "Tournament déjà démarré");
        Preconditions.checkState(structure != null && structure.isValid(), "Structure invalide");

        // Conservation de la structure
        this.structure = structure;

        // Position au 1er round de blinds
        timer = new Timer("Structure", true);

        // Gestion du timer des blinds
        currentBlindRound = 1;
        long frequency = structure.getDuration() * 60000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentBlindRound++;
            }
        }, frequency, frequency);


        // Création de la table et ajout des joueurs
        table = Table.builder().id("1").name("table1").build(); // TODO gérer proprement l'id et nom de table
        table.placePlayers(this);

        clock = new GameClock();
        clock.start(LocalDateTime.now());
        started = true;

    }

    /**
     * Création d'une nouvelle main (partie)
     *
     * @return la main créée
     */
    public Play newPlay() {
        Preconditions.checkState(started, "Tournament non démarré");
        Preconditions.checkState(winner == null, "Tournament terminé");
        Play play = table.newPlay();
        lastPlays.add(play);
        return play;
    }

    /**
     * Nombre de joueurs inscrits
     *
     * @return le nombre de joueurs
     */
    public int nbPlayersIn() {
        return players.size();
    }


    /**
     * Nombre de joueurs encore en jeu
     *
     * @return le nombre de joueurs en jeu
     */
    public int nbRemainingPlayers() {
        return (int) players.stream().filter(p -> !p.isOut()).count();
    }


    /**
     * Fin d'une main
     */
    public void checkEnd() {
        if (nbRemainingPlayers() == 1) {
            winner = players.stream().filter(p -> !p.isOut()).findFirst().get();

            // Stop du timer
            timer.cancel();
            // Enregistrement date de fin
            clock.setEndTime(LocalDateTime.now());
        }

        // TODO gérer cassage de table pour multitables
    }


}
