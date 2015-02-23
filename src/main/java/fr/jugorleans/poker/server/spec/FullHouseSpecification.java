package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un full house
 */
public class FullHouseSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un FullHouseSpecification sur un board donnée
     *
     * @param board le board
     */
    public FullHouseSpecification(Board board){
        this.board = board;
    }


    /**
     * {@inheritDoc}
     *
     * Un full house est la combinaison d'un brelan et d'une paire
     */
    @Override
    public boolean isSatisfiedBy(Hand hand) {
        PairSpecification pairSpecification = new PairSpecification(this.board);
        ThreeOfKindSpecification threeOfKindSpecification = new ThreeOfKindSpecification(this.board);
        return pairSpecification.isSatisfiedBy(hand) && threeOfKindSpecification.isSatisfiedBy(hand);
    }
}