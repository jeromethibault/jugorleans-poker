package fr.jugorleans.poker.server.populator.test;

import fr.jugorleans.poker.server.core.*;
import fr.jugorleans.poker.server.populator.HightPopulator;
import fr.jugorleans.poker.server.populator.PairPopulator;
import fr.jugorleans.poker.server.util.ListCard;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.populator.PairPopulator}
 */
public class PairPopulatorTest {

    /**
     * Le populator testé
     */
    private PairPopulator pairPopulator = new PairPopulator();

    @Test
    public void testHandleCombinationKO(){
        Assert.assertFalse(pairPopulator.handleCombination(Combination.FLUSH));
    }

    @Test
    public void testHandleCombinationOK(){
        Assert.assertTrue(pairPopulator.handleCombination(Combination.PAIR));
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

        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.SPADES)
                .secondCard(CardValue.EIGHT, CardSuit.SPADES).build();
        int combination = pairPopulator.populate(ListCard.newArrayList(board, hand)).getStrenght();
        int combination2 = CombinationStrenght.name(Combination.PAIR).of(CardValue.EIGHT).with(CardValue.ACE).getStrenght();
        Assert.assertEquals(combination2,combination);
    }
}
