package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.tournament.Player;
import fr.jugorleans.poker.server.core.tournament.Seat;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;

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
     * Joueur qui est en train de jouer (identifié par son siège)
     */
    private Seat seatCurrentPlayer;

    /**
     * Récupération du prochain joueur
     * @return le prochain joueur
     */
    public Player whoIsNext(){
        int nextSeatPlayer = (1 + seatCurrentPlayer.getNumber()) % players.size();
        return players.keySet().stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer && !p.isOut()))
                .findFirst()
                .get();

    }

}
