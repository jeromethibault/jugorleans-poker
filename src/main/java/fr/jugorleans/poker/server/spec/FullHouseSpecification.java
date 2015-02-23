package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un full house
 */
public class FullHouseSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link FullHouseSpecification} sur un board donné
     *
     * @param board le board
     */
    public FullHouseSpecification(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Un full house est la combinaison d'un brelan et d'une paire
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        final PairSpecification pairSpecification = new PairSpecification(this.board);
        final ThreeOfKindSpecification threeOfKindSpecification = new ThreeOfKindSpecification(this.board);
        final FourOfKindSpecification fourOfKindSpecification = new FourOfKindSpecification(this.board);
        return !fourOfKindSpecification.isSatisfiedBy(hand) && pairSpecification.isSatisfiedBy(hand) && threeOfKindSpecification.isSatisfiedBy(hand);
    }
}