package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Card;
import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Hand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent une suite
 */
public class StraightSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

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
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());

        /*Trier les cartes via leur force par ordre croissant*/
        List<CardValue> listValue = listCard.stream().map(card -> card.getCardValue()).sorted((c1,c2) -> Integer.compare(c1.getForce(),c2.getForce()))
                .collect(Collectors.toList());

        int firstStraight = listValue.get(4).getForce() - listValue.get(0).getForce();
        int secondStraight = listValue.get(5).getForce() - listValue.get(1).getForce();
        int thirdStraight = listValue.get(6).getForce() - listValue.get(2).getForce();

        return (firstStraight == 4) || (secondStraight == 4) || (thirdStraight == 4);
    }
}