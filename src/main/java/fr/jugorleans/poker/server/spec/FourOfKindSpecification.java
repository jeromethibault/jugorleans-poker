package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un carré
 */
public class FourOfKindSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un FourPairSpecification sur un board donnée
     *
     * @param board le board
     */
    public FourOfKindSpecification(Board board){
        this.board = board;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(Hand hand) {
        int nbUniqueCardOnBoard = board.nbUniqueValueCard();
        int nbFirstCard = board.nb(hand.getFirstCard().getCardValue());
        int nbSecondCard = board.nb(hand.getSecondCard().getCardValue());
        boolean fourWithOneCardHand = (!hand.isPocket() && nbUniqueCardOnBoard == 3 && (nbFirstCard == 3 || nbSecondCard == 3));
        boolean fourWithPocket = (hand.isPocket() && nbFirstCard == 2);
        boolean fourOnBoard = (nbUniqueCardOnBoard == 2 && nbFirstCard == 0 && nbSecondCard == 0);
        return fourOnBoard || fourWithPocket || fourWithOneCardHand;
    }
}