package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.spec.TwoPairSpecification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.TwoPairSpecification}
 */
public class TwoPairSpecificationTest {

    @Test
    public void testNoTwoPair() {
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
        TwoPairSpecification specification = new TwoPairSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testTwoPair() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.SIX).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.FIVE, CardSuit.SPADES).secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        TwoPairSpecification specification = new TwoPairSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testTwoPairOnBoard() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.SPADES).build();
        TwoPairSpecification specification = new TwoPairSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFourOfKind() {
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.ACE, CardSuit.DIAMONDS).build();
        TwoPairSpecification specification = new TwoPairSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    /**
     * Board => ACKS3SKHJS
     * Hand => 3CAD
     */
    @Test
    public void test(){
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
        TwoPairSpecification specification = new TwoPairSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }
}
