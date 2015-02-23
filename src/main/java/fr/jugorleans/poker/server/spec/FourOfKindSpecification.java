package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un carré
 */
public class FourOfKindSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link FourOfKindSpecification} sur un board donné
     *
     * @param board le board
     */
    public FourOfKindSpecification(final Board board) {
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
        final boolean fourWithOneCardHand = (!hand.isPocket() && (nbUniqueCardOnBoard == 3) && ((nbFirstCard == 3) || (nbSecondCard == 3)));
        final boolean fourWithPocket = (hand.isPocket() && (nbFirstCard == 2));
        final boolean fourOnBoard = ((nbUniqueCardOnBoard == 2) && (nbFirstCard == 0) && (nbSecondCard == 0));
        return fourOnBoard || fourWithPocket || fourWithOneCardHand;
    }
}