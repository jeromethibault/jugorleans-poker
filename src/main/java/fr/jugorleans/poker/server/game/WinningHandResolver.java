package fr.jugorleans.poker.server.game;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

import java.util.List;

/**
 * WinnigHandResolver interface.
 */
public interface WinningHandResolver {

    /**
     * Déterminer les mains gagnantes sur un board donné
     *
     * @param board    le board
     * @param listHand la liste des mains
     * @return la liste des mains gagnantes
     */
    List<Hand> getWinningHand(Board board, List<Hand> listHand);
}
