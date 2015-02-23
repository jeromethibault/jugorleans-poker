package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent un brelan
 */
public class ThreeOfKindSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un ThreePairSpecification sur un board donnée
     *
     * @param board le board
     */
    public ThreeOfKindSpecification(Board board){
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
        boolean threeWithOneCardHand = (!hand.isPocket() && nbUniqueCardOnBoard == 4 && (nbFirstCard == 2 || nbSecondCard == 2));
        boolean threeWithPocket = (hand.isPocket() && nbFirstCard == 1);
        boolean threeOnBoard = ((nbUniqueCardOnBoard == 3 || nbUniqueCardOnBoard == 2)  && nbFirstCard == 0 && nbSecondCard == 0);
        return threeOnBoard || threeWithPocket || threeWithOneCardHand;
    }
}