package fr.jugorleans.poker.server.game;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Combination;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Interface définissant ce que doit être une classe permettant de déterminer
 * la combinasion en fonction d'un board et d'une main
 */
public interface HandResolver {

    /**
     * Déterminer la combinaison la plus haute en fonction d'un
     * board et d'une main
     *
     * @param board le tableau
     * @param hand la main
     * @return la combinaison
     */
    Combination resolve(Board board, Hand hand);
}
