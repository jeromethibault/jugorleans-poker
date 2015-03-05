package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.Hand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent une flush
 */
public class FlushSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link FlushSpecification} sur un board donné
     *
     * @param board le board
     */
    public FlushSpecification(final Board board) {
        this.board = board;
    }

    private int nbSuited(CardSuit cardSuit, List<Card> list) {
        return list.stream().map(card -> card.getCardSuit().equals(cardSuit) ? 1 : 0).collect(Collectors.toList())
                .stream().collect(Collectors.summingInt(i -> i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(Hand hand) {
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        if (hand != null) {
            listCard.addAll(hand.getCards());
        }

        int nbSuitedMax = listCard.stream().map(card -> card.getCardSuit()).collect(Collectors.toSet())
                .stream().map(suit -> nbSuited(suit, listCard)).collect(Collectors.toList())
                .stream().mapToInt(i -> i).max().getAsInt();

        return nbSuitedMax >= 5;
    }
}
