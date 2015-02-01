package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.core.*;
import fr.jugorleans.poker.server.spec.PairSpecification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.PairSpecification}
 */
public class PairSpecificationTest {

    @Test
    public void testNoPair(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE,CardSuit.SPADES).secondCard(CardValue.JACK,CardSuit.SPADES).build();
        PairSpecification specification = new PairSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testPair(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);

        Hand hand = Hand.newBuilder().firstCard(CardValue.FIVE,CardSuit.SPADES).secondCard(CardValue.JACK,CardSuit.SPADES).build();
        PairSpecification specification = new PairSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }
}
