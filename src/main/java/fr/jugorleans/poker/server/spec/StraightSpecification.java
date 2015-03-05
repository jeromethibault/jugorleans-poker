package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.util.ListCard;

import java.util.List;

/**
 * Specification permettant d'évaluer si la main et le board constituent une suite
 */
public class StraightSpecification implements Specification<Hand> {

    /**
     * Spécification interne permettant de combiner la specification sur une suite
     * avec les autres
     */
    private class InternalSpecification implements Specification<Hand> {

        private final Board board;

        public InternalSpecification(final Board board) {
            this.board = board;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfiedBy(final Hand hand) {
            List<Card> listCard = ListCard.newArrayList(this.board, hand);
            List<CardValue> setValue = ListCard.orderAscByForce(listCard);

            if (setValue.size() >= NB_STRAIGHT_CARD) {
                int modulo = setValue.size() % NB_STRAIGHT_CARD;
                for (int i = 0; i <= modulo; i++) {
                    int straight = setValue.get(4 + i).getForce() - setValue.get(i).getForce();
                    if (straight == 4) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Le board
     */
    private final Board board;

    /**
     * Nombre de carte nécessaire pour constituer une quinte
     */
    public static final int NB_STRAIGHT_CARD = 5;

    /**
     * Construire un {@link fr.jugorleans.poker.server.spec.StraightSpecification} sur un board donné
     *
     * @param board le board
     */
    public StraightSpecification(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        InternalSpecification internalSpecification = new InternalSpecification(board);
        FlushSpecification flushSpecification = new FlushSpecification(board);
        return internalSpecification.and(flushSpecification.negate()).isSatisfiedBy(hand);
    }
}