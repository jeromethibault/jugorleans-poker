package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.FourOfKindPopulator;
import fr.jugorleans.poker.server.populator.ThreeOfKindPopulator;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.FourOfKindPopulator}
 */
public class FourOfKindPopulatorTest {

    /**
     * Le populator testé
     */
    private FourOfKindPopulator fourOfKindPopulator = new FourOfKindPopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(fourOfKindPopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(fourOfKindPopulator.handleCombination(Combination.FOUR_OF_KIND));
    }

    @Test
    public void testPopulate(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.DIAMONDS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.SIX).suit(CardSuit.DIAMONDS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.SPADES).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.QUEEN).suit(CardSuit.CLUBS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.FOUR, CardSuit.CLUBS).build();
        int combination = fourOfKindPopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.FOUR_OF_KIND).of(CardValue.FOUR).with(CardValue.ACE).getStrength();
        Assert.assertEquals(combination2,combination);
    }
}
