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
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());
        Map<CardValue, Long> counters = listCard.stream().collect(Collectors.groupingBy(Card::getCardValue, Collectors.counting()));
        List<Long> list = counters.values().stream().filter(l -> (l == 3 || l == 2)).collect(Collectors.toList());
        final FourOfKindSpecification fourOfKindSpecification = new FourOfKindSpecification(this.board);
        return fourOfKindSpecification.negate().isSatisfiedBy(hand) && list.contains(3L) && list.contains(2L);
    }
}