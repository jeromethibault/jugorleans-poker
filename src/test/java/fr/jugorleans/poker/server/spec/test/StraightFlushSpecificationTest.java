package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.core.*;
import fr.jugorleans.poker.server.spec.StraightFlushSpecification;
import fr.jugorleans.poker.server.spec.StraightSpecification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.StraightFlushSpecification}
 */
public class StraightFlushSpecificationTest {

    @Test
    public void testNoStraightFlush() {
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
        StraightFlushSpecification specification = new StraightFlushSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFourStraightOnBoard() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.DIAMONDS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.JACK).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.QUEEN).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.FIVE, CardSuit.SPADES).secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        StraightFlushSpecification specification = new StraightFlushSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFlushStraight() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.JACK).suit(CardSuit.CLUBS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.CLUBS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.QUEEN, CardSuit.CLUBS).secondCard(CardValue.TEN, CardSuit.DIAMONDS).build();
        StraightFlushSpecification specification = new StraightFlushSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testFlushStraight2() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.JACK, CardSuit.CLUBS).secondCard(CardValue.QUEEN, CardSuit.CLUBS).build();
        StraightFlushSpecification specification = new StraightFlushSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

}
