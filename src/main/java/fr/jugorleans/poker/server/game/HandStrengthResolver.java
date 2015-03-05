package fr.jugorleans.poker.server.game;

import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Hand;

/**
 * HandStrenghtResolver interface
 */
public interface HandStrengthResolver {

    /**
     * Déterminer la force d'une main
     *
     * @param hand la main
     * @param board le tableau
     * @return la force de la main soud forme d'entier
     */
    int getHandStrenght(Hand hand, Board board);
}
