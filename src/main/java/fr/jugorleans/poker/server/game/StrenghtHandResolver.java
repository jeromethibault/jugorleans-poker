package fr.jugorleans.poker.server.game;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * StrongestHandResolver interface
 */
public interface StrenghtHandResolver {

    /**
     * Déterminer la force d'une main
     *
     * @param hand la main
     * @param board le tableau
     * @return la force de la main soud forme d'entier
     */
    int getStrenghtHand(Hand hand, Board board);
}
