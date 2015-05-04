package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Structure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@Slf4j
/**
 * Tournoi
 */
public class Tournament {

    /**
     * Id
     */
    private String id;

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
    private List<Play> lastPlays = Lists.newArrayList();

    /**
     * Flag de lancement du tournoi
     */
    private boolean started = false;

    /**
     * Table se déroule le tournoi
     */
    private Map<String, Table> tables;

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
     * Initialisation du tournoi
     * @return le tournoi
     */
    public Tournament init() {
        LocalDateTime start = LocalDateTime.now();
        id = start.format(DateTimeFormatter.ISO_DATE_TIME); // TODO gérer clé unique
        clock = new GameClock();
        clock.start(start);

        // Création de la table et ajout des joueurs
        tables = Maps.newHashMap();
        String idTable1 = id + "_" + StringUtils.leftPad("1", 3, '0');
        Table table1 = Table.builder().id(idTable1).name("WSOP").build(); // TODO le nom de table
        tables.put(idTable1, table1);

        return this;
    }

    /**
     * Ajout de joueurs
     *
     * @param playersToAdd joueurs à ajouter
     */
    public Tournament addPlayers(Player... playersToAdd) {
        Preconditions.checkState(id != null, "Tournament non initialisé");
        if (players == null) {
            players = Lists.newArrayList();
        }
        players.addAll(Arrays.stream(playersToAdd).collect(Collectors.toList()));
        return this;
    }

    /**
     * Démarrage du tournoi
     */
    public Tournament start() {
        Preconditions.checkState(id != null, "Tournament non initialisé");
        Preconditions.checkState(!started, "Tournament déjà démarré");
        Preconditions.checkState(players != null && players.size() > 1, "Manque de joueurs");
        Preconditions.checkState(structure != null && structure.isValid(), "Structure invalide");

        // Position au 1er round de blinds
        timer = new Timer("Struct" + id, true);

        // Placement des joueurs
        tables.entrySet().forEach(t -> t.getValue().placePlayers(this)); // TODO à faire évoluer si multitables pour ventiler les joueurs

        // Gestion du timer des blinds
        currentBlindRound = 1;
        long frequency = structure.getDuration() * 60000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentBlindRound++;
            }
        }, frequency, frequency);

        started = true;

        log.info("Démarrage du tournoi {}", id);
        return this;
    }

    /**
     * Création d'une nouvelle main (partie)
     * Va disparaitre avec refactoring service
     *
     * @return la main créée
     */
    public Play newPlay() {
        Preconditions.checkState(winner == null, "Tournament terminé");
        Preconditions.checkState(started, "Tournament non démarré");

        Play play = getTable1().newPlay(); // TODO à reprendre
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

    /**
     * TODO dégager quand les services seront ok avec vrais repositories
     *
     * @return la table1
     */
    public Table getTable1() {
        return tables.get(id + "_001");
    }


}
