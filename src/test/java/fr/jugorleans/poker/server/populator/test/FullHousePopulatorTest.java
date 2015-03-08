package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.FullHousePopulator;
import fr.jugorleans.poker.server.populator.ThreeOfKindPopulator;
import fr.jugorleans.poker.server.spec.FullHouseSpecification;
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

    /**
     * Board => 4HJH4S2H9C
     * Hand => JC4C
     */
    @Test
    public void testPopulate2(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.HEARTS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.JACK).suit(CardSuit.HEARTS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.SPADES).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.CLUBS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.JACK, CardSuit.CLUBS).secondCard(CardValue.FOUR, CardSuit.CLUBS).build();
        int combination = fullHousePopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.FULL_HOUSE).of(CardValue.FOUR).and(CardValue.JACK).getStrength();
        Assert.assertEquals(combination2, combination);
    }

    /**
     * Board => 3H10D4S4C10C
     * Hand => 4D10S
     */
    @Test
    public void testPopulate3(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.THREE).suit(CardSuit.HEARTS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.DIAMONDS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.SPADES).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.FOUR).suit(CardSuit.CLUBS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.FOUR, CardSuit.DIAMONDS)
                .secondCard(CardValue.TEN, CardSuit.SPADES).build();
        int combination = fullHousePopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.FULL_HOUSE).of(CardValue.TEN).and(CardValue.FOUR).getStrength();
        Assert.assertEquals(combination2, combination);

    }
}
