package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.StraightFlushPopulator;
import fr.jugorleans.poker.server.populator.StraightPopulator;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.StraightFlushPopulator}
 */
public class StraightFlushPopulatorTest {

    /**
     * Le populator testé
     */
    private StraightFlushPopulator straightFlushPopulator = new StraightFlushPopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(straightFlushPopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(straightFlushPopulator.handleCombination(Combination.STRAIGHT_FLUSH));
    }

    @Test
    public void testPopulate(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.SPADES).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.SPADES).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.SPADES).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.SIX, CardSuit.SPADES)
                .secondCard(CardValue.TEN, CardSuit.SPADES).build();
        int combination = straightFlushPopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.STRAIGHT_FLUSH).of(CardValue.TEN).getStrength();
        Assert.assertEquals(combination2,combination);
    }
}
