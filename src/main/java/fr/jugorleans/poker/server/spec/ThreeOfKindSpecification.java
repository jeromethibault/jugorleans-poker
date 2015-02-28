package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Card;
import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent un brelan
 */
public class ThreeOfKindSpecification implements Specification<Hand> {

    /**
     * Spécification interne
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
            final int nbUniqueCardOnBoard = board.nbUniqueValueCard();
            final int nbFirstCard = board.nb(hand.getFirstCard().getCardValue());
            final int nbSecondCard = board.nb(hand.getSecondCard().getCardValue());
            final boolean threeWithOneCardHand = (!hand.isPocket() && (nbUniqueCardOnBoard == 4) && ((nbFirstCard == 2) || (nbSecondCard == 2)));
            final boolean threeWithPocket = (hand.isPocket() && (nbFirstCard == 1));
            final boolean threeOnBoard = (((nbUniqueCardOnBoard == 3) || (nbUniqueCardOnBoard == 2)) && (nbFirstCard == 0) && (nbSecondCard == 0));
            return threeOnBoard || threeWithPocket || threeWithOneCardHand;
        }

    }

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
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());
        Map<CardValue, Long> counters = listCard.stream().collect(Collectors.groupingBy(Card::getCardValue, Collectors.counting()));
        InternalSpecification internalSpecification = new InternalSpecification(board);
        List<Long> list = counters.values().stream().filter(l -> l == 2).collect(Collectors.toList());
        return internalSpecification.isSatisfiedBy(hand) && list.size() == 0;
    }
}