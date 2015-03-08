package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.spec.FlushSpecification;
import fr.jugorleans.poker.server.spec.FullHouseSpecification;
import fr.jugorleans.poker.server.spec.TwoPairSpecification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.FullHouseSpecification}
 */
public class FullHouseSpecificationTest {

    @Test
    public void testNoFullHoue() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.SPADES).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFullHouse() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.DIAMONDS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.EIGHT, CardSuit.SPADES).secondCard(CardValue.NINE, CardSuit.SPADES).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFullHouse2() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.TEN, CardSuit.SPADES).secondCard(CardValue.TEN, CardSuit.DIAMONDS).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFullHouse3() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.DIAMONDS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.SPADES).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.DIAMONDS).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFullHouse4() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.DIAMONDS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.SPADES).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.DIAMONDS).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFullHouse5() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.KING).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.KING, CardSuit.HEARTS).build();
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    /**
     * Board => 4HJH4S2H9C
     * Hand => JC4C
     */
    @Test
    public void testFullHouse6(){
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
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    /**
     * Board => 3H10D4S4C10C
     * Hand => 4D10S
     */
    @Test
    public void testFullHouse7(){
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
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));

    }

    /**
     * Board => ACKS3SKHJS
     * Hand => 3CAD
     */
    @Test
    public void testFullHouseKO(){
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
        FullHouseSpecification specification = new FullHouseSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }
}
