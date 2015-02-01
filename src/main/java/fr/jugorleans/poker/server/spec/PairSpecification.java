package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent une
 * paire
 */
public class PairSpecification extends AbstractSpecification<Hand>{

    /**
     * Le board
     */
    private Board board;

    /**
     * Construire un PairSpecification sur un board donnée
     *
     * @param board le board
     */
    public PairSpecification(Board board){
        this.board = board;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(Hand hand) {
        int nbFirstCard = board.nb(hand.getFirstCard().getCardValue());
        int nbSecondCard = board.nb(hand.getSecondCard().getCardValue());
        return (nbFirstCard == 1 && nbSecondCard == 0) || (nbFirstCard == 0 && nbSecondCard == 1);
    }
}