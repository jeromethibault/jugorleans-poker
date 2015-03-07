package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.FlushPopulator;
import fr.jugorleans.poker.server.populator.TwoPairPopulator;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.FlushPopulator}
 */
public class FlushPopulatorTest {

    /**
     * Le populator testé
     */
    private FlushPopulator flushPopulator = new FlushPopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(flushPopulator.handleCombination(Combination.STRAIGHT));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(flushPopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testPopulate(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.HEARTS)
                .secondCard(CardValue.KING, CardSuit.HEARTS).build();
        int combination = flushPopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.FLUSH).of(CardValue.ACE).getStrength();
        Assert.assertEquals(combination2,combination);
    }
}
