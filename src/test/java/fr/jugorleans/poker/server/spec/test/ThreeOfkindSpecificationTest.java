package fr.jugorleans.poker.server.spec.test;

import fr.jugorleans.poker.server.core.*;
import fr.jugorleans.poker.server.spec.ThreeOfKindSpecification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.spec.ThreeOfKindSpecification}
 */
public class ThreeOfkindSpecificationTest {

    @Test
    public void testNoThreeOfKind() {
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
        ThreeOfKindSpecification specification = new ThreeOfKindSpecification(board);
        Assert.assertFalse(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testThreeOfKindWithOnCardHand() {
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.FIVE, CardSuit.SPADES).secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        ThreeOfKindSpecification specification = new ThreeOfKindSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testThreeOfKindWithPocket() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.NINE).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.TEN, CardSuit.SPADES).secondCard(CardValue.TEN, CardSuit.DIAMONDS).build();
        ThreeOfKindSpecification specification = new ThreeOfKindSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }

    @Test
    public void testTheeOfKindOnBoard() {
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.DIAMONDS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.DIAMONDS).build();
        ThreeOfKindSpecification specification = new ThreeOfKindSpecification(board);
        Assert.assertTrue(specification.isSatisfiedBy(hand));
    }
}
