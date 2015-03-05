package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent une suite "flushée"
 */
public class StraightFlushSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link fr.jugorleans.poker.server.spec.StraightFlushSpecification} sur un board donné
     *
     * @param board le board
     */
    public StraightFlushSpecification(final Board board) {
        this.board = board;
    }

    /**
     * @param startIndex index de début de suite
     * @param endIndex   index de fin de suite
     * @param listCard   la liste de carte
     * @return true si la quinte est "flushée", false sinon
     */
    private boolean isFlush(int startIndex, int endIndex, List<Card> listCard) {
        List<Card> straight = Lists.newArrayList(listCard.subList(startIndex, endIndex));
        Board newBoard = new Board();
        straight.stream().forEach(card -> newBoard.addCard(card));
        FlushSpecification flushSpecification = new FlushSpecification(newBoard);
        return flushSpecification.isSatisfiedBy(null);
    }

    /**
     * Rechercher la carte en doublon à retirer de la liste. On doit conserver
     * la carte qui possède la plus grande famille dans le board
     *
     * @param cardValue la valeur en doublon
     * @param list      la liste des cartes
     * @return la carte à retirer
     */
    private Card getCardToRemove(CardValue cardValue, List<Card> list) {
        Map<CardSuit, Long> counters = list.stream().collect(Collectors.groupingBy(Card::getCardSuit, Collectors.counting()));
        List<Card> cards = list.stream().filter(card -> card.getCardValue().equals(cardValue)).collect(Collectors.toList());

        Card cardToEliminate = null;
        for (Card card : cards) {
            if (cardToEliminate == null) {
                cardToEliminate = card;
            } else {
                if (counters.get(cardToEliminate.getCardSuit()) > counters.get(card.getCardSuit())) {
                    cardToEliminate = card;
                }
            }
        }

        return cardToEliminate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());

        /*Trier les cartes via leur force par ordre croissant*/
        List<Card> setValue = listCard.stream().sorted((c1, c2) -> Integer.compare(c1.getCardValue().getForce(), c2.getCardValue().getForce())).collect(Collectors.toList());

        /*En cas de doublon ne garder que la carte possédant la plus grande famille sur le board*/
        Map<CardValue, Long> counters = setValue.stream().collect(Collectors.groupingBy(Card::getCardValue, Collectors.counting()));

        counters.keySet().stream().forEach(cardValue -> {
            if (counters.get(cardValue) > 1) {
                setValue.remove(getCardToRemove(cardValue, setValue));
            }
        });
        if (setValue.size() >= StraightSpecification.NB_STRAIGHT_CARD) {
            int modulo = setValue.size() % StraightSpecification.NB_STRAIGHT_CARD;
            for (int i = 0; i <= modulo; i++) {
                int straight = setValue.get(4 + i).getCardValue().getForce() - setValue.get(i).getCardValue().getForce();
                if (straight == 4 && isFlush(i, 5 + i, setValue)) {
                    return true;
                }
            }
        }

        return false;
    }
}