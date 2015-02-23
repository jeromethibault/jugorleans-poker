package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent une double
 * paire
 */
public class TwoPairSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un TwoPairSpecification sur un board donnée
     *
     * @param board le board
     */
    public TwoPairSpecification(Board board){
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
        return (nbFirstCard == 1 && nbSecondCard == 1) || (nbUniqueCardOnBoard == 3);
    }
}