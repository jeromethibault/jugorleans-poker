package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.FullHousePopulator;
import fr.jugorleans.poker.server.populator.ThreeOfKindPopulator;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.FullHousePopulator}
 */
public class FullHousePopulatorTest {

    /**
     * Le populator testé
     */
    private FullHousePopulator fullHousePopulator = new FullHousePopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(fullHousePopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(fullHousePopulator.handleCombination(Combination.FULL_HOUSE));
    }

    @Test
    public void testPopulate(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.EIGHT, CardSuit.DIAMONDS)
                .secondCard(CardValue.FIVE, CardSuit.SPADES).build();
        int combination = fullHousePopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.FULL_HOUSE).of(CardValue.EIGHT).and(CardValue.FIVE).getStrength();
        Assert.assertEquals(combination2,combination);
    }
}
