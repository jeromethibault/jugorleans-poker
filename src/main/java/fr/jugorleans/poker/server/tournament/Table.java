package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Table de jeu
 */
@Getter
@Setter
@Builder
@ToString
public class Table {

    /**
     * ID de la table
     */
    private String id;

    /**
     * Nom de la table
     */
    private String name;

    /**
     * Dealer de la main courante (identifié par son numéro de siège)
     */
    private Integer seatPlayDealer;

    /**
     * Main courante
     */
    private Play currentPlay;

    /**
     * Tournoi
     */
    private Tournament tournament;

    /**
     * Placement des joueurs
     *
     * @param tournament tournoi
     */
    protected void placePlayers(Tournament tournament) {
        this.tournament = tournament;

        AtomicInteger seatNumber = new AtomicInteger(0);
        tournament.getPlayers().stream().forEach(p -> {
            // Attribution des places
            p.setSeat(Seat.builder().number(seatNumber.incrementAndGet()).build());

            // Initialisation des stacks
            p.setStack(tournament.getInitialStack());
        });

    }

    /**
     * Création d'une nouvelle main (partie)
     *
     * @return la main créée
     */
    protected Play newPlay() {
        Preconditions.checkState(tournament != null, "Joueurs non placés");
        currentPlay = Play.builder().build();

        // TODO gérer multitables --> dealer au niveau de la table et pas du tournament

        // Déplacement du dealer
        moveDealerButton();

        // Démarrage de la main
        currentPlay.start(tournament);

        return currentPlay;
    }

    /**
     * Déplacement du dealer
     */
    private void moveDealerButton() {
        if (seatPlayDealer == null) {
            // Choix aléatoire du tout premier dealer
            seatPlayDealer = ((Double) (Math.random() * tournament.getPlayers().size())).intValue();
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

    /**
     * Recherche du joueur suivant
     *
     * @param seatCurrentPlayer joueur courant
     * @return le jouer
     */
    private Optional<Player> findNextPlayer(int seatCurrentPlayer) {
        List<Player> players = tournament.getPlayers();
        int nextSeatPlayer = 1 + seatCurrentPlayer % players.size();
        return players.stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer
                        && !p.isOut()))
                .findFirst();
    }

}
