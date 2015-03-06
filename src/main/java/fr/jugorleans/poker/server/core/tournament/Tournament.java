package fr.jugorleans.poker.server.core.tournament;

import fr.jugorleans.poker.server.core.play.Play;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Tournoi
 */
@Getter
@Setter
@Builder
@ToString
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
    private Integer nbMaxPlayers = 10;

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
    public void start() {
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
        Assert.isTrue(started, "Tournament non démarré");
        currentPlay = Play.builder().build();
        lastPlays.add(currentPlay);


        // Déplacement du dealer
        moveDealerButton();

        // Démarrage de la main
        currentPlay.start(this);

        return currentPlay;
    }

    private void moveDealerButton() {
        if (seatPlayDealer == null){
            // Choix aléatoire du tout premier dealer
            seatPlayDealer = ((Double) (Math.random() * players.size())).intValue();
        } else {
            // TODO gérer déplacement en fonction des présents
        }
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
