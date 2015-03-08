package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.populator.PairPopulator;
import fr.jugorleans.poker.server.populator.TwoPairPopulator;
import fr.jugorleans.poker.server.spec.TwoPairSpecification;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.TwoPairPopulator}
 */
public class TwoPairPopulatorTest {

    /**
     * Le populator testé
     */
    private TwoPairPopulator twoPairPopulator = new TwoPairPopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(twoPairPopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(twoPairPopulator.handleCombination(Combination.TWO_PAIR));
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.FIVE, CardSuit.SPADES)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        int combination = twoPairPopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.TWO_PAIR).of(CardValue.EIGHT).and(CardValue.FIVE).with(CardValue.NINE).getStrength();
        Assert.assertEquals(combination2,combination);
    }

    /**
     * Board => ACKS3SKHJS
     * Hand => 3CAD
     */
    @Test
    public void testPopulate2(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.KING).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.THREE).suit(CardSuit.SPADES).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.KING).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.JACK).suit(CardSuit.SPADES).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.THREE, CardSuit.CLUBS).secondCard(CardValue.ACE, CardSuit.DIAMONDS).build();
        int combination = twoPairPopulator.populate(ListCard.newArrayList(board, hand)).getStrength();
        int combination2 = CombinationStrength.name(Combination.TWO_PAIR).of(CardValue.ACE).and(CardValue.KING).with(CardValue.JACK).getStrength();
        Assert.assertEquals(combination2,combination);
    }
}
