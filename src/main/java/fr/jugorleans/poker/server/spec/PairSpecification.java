package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent une paire
 */
public class PairSpecification implements Specification<Hand> {

    /**
     * Spécification interne définissant une paire pouvant être combiné
     * avec d'autre spécification
     */
    private class InternalSpecification implements Specification<Hand> {

        private final Board board;

        public InternalSpecification(final Board board) {
            this.board = board;
        }

        @Override
        public boolean isSatisfiedBy(Hand hand) {
            final int nbUniqueCardOnBoard = this.board.nbUniqueValueCard();
            final int nbFirstCard = this.board.nb(hand.getFirstCard().getCardValue());
            final int nbSecondCard = this.board.nb(hand.getSecondCard().getCardValue());
            return ((nbFirstCard == 1) && (nbSecondCard == 0)) || ((nbFirstCard == 0) && (nbSecondCard == 1))
                    || (nbUniqueCardOnBoard == 4) || (nbUniqueCardOnBoard == 2);
        }
    }

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link PairSpecification} sur un board donné
     *
     * @param board le board
     */
    public PairSpecification(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());
        InternalSpecification internalSpecification = new InternalSpecification(board);
        Map<CardValue, Long> counters = listCard.stream().collect(Collectors.groupingBy(Card::getCardValue, Collectors.counting()));
        List<Long> list = counters.values().stream().filter(l -> (l == 3 || l == 4)).collect(Collectors.toList());
        return internalSpecification.isSatisfiedBy(hand) && list.size() == 0;
    }
}