package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent une
 * flush
 */
public class FlushSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un FlushSpecification sur un board donnée
     *
     * @param board le board
     */
    public FlushSpecification(Board board){
        this.board = board;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(Hand hand) {
        // TODO
        return false;
    }
}
