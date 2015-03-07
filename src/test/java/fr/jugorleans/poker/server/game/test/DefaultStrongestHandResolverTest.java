package fr.jugorleans.poker.server.game.test;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.game.StrongestHandResolver}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
public class DefaultStrongestHandResolverTest {

    @Autowired
    private DefaultStrongestHandResolver defaultStrongestHandResolver;

    @Test
    public void testConfSpring(){
        Assert.assertNotNull(defaultStrongestHandResolver);
    }

    @Test
    public void test(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();

        Hand hand2 = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES)
                .secondCard(CardValue.TEN, CardSuit.SPADES).build();

        List<Hand> list = defaultStrongestHandResolver.getWinningHand(board, Lists.newArrayList(hand,hand2));
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(CardValue.ACE,list.get(0).getFirstCard().getCardValue());
        Assert.assertEquals(CardValue.TEN,list.get(0).getSecondCard().getCardValue());
    }

    @Test
    public void test2(){
        Board board = new Board();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.TEN).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();

        Hand hand2 = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.DIAMONDS)
                .secondCard(CardValue.TEN, CardSuit.SPADES).build();

        Hand hand3 = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.CLUBS)
                .secondCard(CardValue.TEN, CardSuit.DIAMONDS).build();

        List<Hand> list = defaultStrongestHandResolver.getWinningHand(board, Lists.newArrayList(hand,hand2,hand3));
        Assert.assertEquals(2,list.size());
    }

}
