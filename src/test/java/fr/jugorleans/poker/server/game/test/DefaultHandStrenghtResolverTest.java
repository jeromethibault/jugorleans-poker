package fr.jugorleans.poker.server.game.test;

import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.game.DefaultHandStrengthResolver;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.game.DefaultHandStrengthResolver}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
public class DefaultHandStrenghtResolverTest {

    @Autowired
    private DefaultHandStrengthResolver defaultHandStrenghtResolver;

    @Test
    public void testSpringConfiguration(){
        Assert.assertNotNull(defaultHandStrenghtResolver);
    }

    @Test
    public void testHighCombination(){
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.THREE, CardSuit.SPADES).secondCard(CardValue.JACK, CardSuit.SPADES).build();

        int combination = defaultHandStrenghtResolver.getHandStrenght(hand,board);
        int combination2 = CombinationStrength.name(Combination.HIGH).of(CardValue.ACE).with(CardValue.JACK).getStrength();
        Assert.assertEquals(combination2,combination);
    }

    @Test
    public void testPairCombination(){
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        int combination = defaultHandStrenghtResolver.getHandStrenght(hand, board);
        int combination2 = CombinationStrength.name(Combination.PAIR).of(CardValue.EIGHT).with(CardValue.ACE).getStrength();
        Assert.assertEquals(combination2,combination);
    }

    @Test
    public void testTwoPairCombination(){
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.EIGHT, CardSuit.SPADES)
                .secondCard(CardValue.FIVE, CardSuit.SPADES).build();
        int combination = defaultHandStrenghtResolver.getHandStrenght(hand, board);
        int combination2 = CombinationStrength.name(Combination.TWO_PAIR).of(CardValue.EIGHT).and(CardValue.FIVE).with(CardValue.NINE).getStrength();
        Assert.assertEquals(combination2,combination);
    }

    @Test
    public void testThreeOfKingCombination(){
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.EIGHT, CardSuit.HEARTS)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        int combination = defaultHandStrenghtResolver.getHandStrenght(hand, board);
        int combination2 = CombinationStrength.name(Combination.THREE_OF_KIND).of(CardValue.EIGHT).with(CardValue.NINE).getStrength();
        Assert.assertEquals(combination2,combination);
    }

}
