package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Seat;
import fr.jugorleans.poker.server.core.play.Structure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
 * TODO gérer le changement de round (blinds) avec timer à initialiser au start
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
     * Main courante
     */
    private Play currentPlay;

    /**
     * Flag de lancement du tournoi
     */
    private boolean started = false;


    /**
     * Dealer de la main courante (identifié par son numéro de siège)
     */
    private Integer seatPlayDealer;

    /**
     * Structure du tournoi
     */
    private Structure structure;

    /**
     * Round (blinds) courant
     */
    private Integer currentBlindRound;


    /**
     * Ajout de joueurs
     *
     * @param playersToAdd joueurs à ajouter
     */
    public void addPlayers(Player... playersToAdd) {
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
        Timer timer = new Timer("Structure", true);

        // Gestion du timer des blinds
        currentBlindRound = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentBlindRound++;
                System.out.println("********************************************************");
            }
        }, 0, structure.getDuration() * 60000) ;

        AtomicInteger seatNumber = new AtomicInteger(0);
        players.stream().forEach(p -> {
            // Attribution des places
            p.setSeat(Seat.builder().number(seatNumber.incrementAndGet()).build());

            // Initialisation des stacks
            p.setStack(initialStack);
        });

        started = true;
    }

    /**
     * Création d'une nouvelle main (partie)
     *
     * @return la main créée
     */
    public Play newPlay() {
        Preconditions.checkState(started, "Tournament non démarré");
        currentPlay = Play.builder().build();
        lastPlays.add(currentPlay);


        // TODO gérer multitables --> dealer au niveau de la table et pas du tournament

        // Déplacement du dealer
        moveDealerButton();

        // Démarrage de la main
        currentPlay.start(this);



        return currentPlay;
    }

    private void moveDealerButton() {
        if (seatPlayDealer == null) {
            // Choix aléatoire du tout premier dealer
            seatPlayDealer = ((Double) (Math.random() * players.size())).intValue();
        } else {
            seatPlayDealer = nextPlayer().getSeat().getNumber();
        }
    }

    /**
     * Passage au prochain joueur
     *
     * @return le prochain joueur
     */
    private Player nextPlayer() {
        int seatCurrentPlayer = seatPlayDealer;
        Optional<Player> next = findNextPlayer(seatCurrentPlayer);
        while (!next.isPresent()) {
            seatCurrentPlayer++;
            next = findNextPlayer(seatCurrentPlayer);
        }

        return next.get();
    }

    private Optional<Player> findNextPlayer(int seatCurrentPlayer) {
        int nextSeatPlayer = 1 + seatCurrentPlayer % players.size();
        return players.stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer
                        && !p.isOut()) && !p.isAllIn())
                .findFirst();
    }

    /**
     * Nombre de joueurs inscrits
     *
     * @return le nombre de joueurs
     */
    public int nbPlayersIn() {
        return players.size();
    }
}
