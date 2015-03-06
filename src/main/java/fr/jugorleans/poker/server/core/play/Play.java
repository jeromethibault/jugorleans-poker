package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.tournament.Player;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

/**
 * Partie (au sens une main) jouée entre plusieurs joueurs
 */
@Builder
@ToString
public class Play {



    /**
     * Pots (principal et éventuels secondaires)
     */
    private Pot pot = new Pot();

    /**
     * Paquet de cartes
     */
    private Deck deck = new Deck();

    /**
     * Board
     */
    private Board board = new Board();

    /**
     * Joueurs et cartes associées
     */
    private Map<Player, Hand> players = Maps.newHashMap();

    /**
     * Joueur qui est en train de jouer (identifié par son numéro siège)
     */
    private int seatCurrentPlayer;

    /**
     * Joueur actuellement dealer (identifié par son numéro de siège)
     */
    private int seatCurrentDealer;

    /**
     * Round courant
     */
    private Round round;

    /**
     * Main démarrée
     */
    private boolean started = false;


    public void start(int seatNewDealer) {
        seatCurrentDealer = seatNewDealer;

        // TODO gerer distribution des cartes

        // TODO gerer les blinds

        // TODO corriger (le prochain jouer est en réalité après les blinds)
        seatCurrentPlayer = seatCurrentDealer;
    }


    /**
     * Passage au prochain joueur
     *
     * @return le prochain joueur
     */
    public Player nextPlayer() {
        Optional<Player> next = findNextPlayer();
        while (!next.isPresent()){
            seatCurrentPlayer++;
            next = findNextPlayer();
        }

        seatCurrentPlayer = next.get().getSeat().getNumber();
        return next.get();
    }

    private Optional<Player> findNextPlayer() {
        int nextSeatPlayer = seatCurrentPlayer % players.size();
        return players.keySet().stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer && !p.isOut()))
                .findFirst();
    }


}
