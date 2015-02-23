package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un brelan
 */
public class ThreeOfKindSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link ThreeOfKindSpecification} sur un board donné
     *
     * @param board le board
     */
    public ThreeOfKindSpecification(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        final int nbUniqueCardOnBoard = this.board.nbUniqueValueCard();
        final int nbFirstCard = this.board.nb(hand.getFirstCard().getCardValue());
        final int nbSecondCard = this.board.nb(hand.getSecondCard().getCardValue());
        final boolean threeWithOneCardHand = (!hand.isPocket() && (nbUniqueCardOnBoard == 4) && ((nbFirstCard == 2) || (nbSecondCard == 2)));
        final boolean threeWithPocket = (hand.isPocket() && (nbFirstCard == 1));
        final boolean threeOnBoard = (((nbUniqueCardOnBoard == 3) || (nbUniqueCardOnBoard == 2)) && (nbFirstCard == 0) && (nbSecondCard == 0));
        return threeOnBoard || threeWithPocket || threeWithOneCardHand;
    }
}