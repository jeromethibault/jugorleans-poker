package fr.jugorleans.poker.server.core.test;

import com.google.common.collect.Sets;
import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Card;
import fr.jugorleans.poker.server.core.Deck;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.Board}
 */
public class BoardTest {

    private Board board = new Board();

    @Test(expected = IllegalArgumentException.class)
    public void addNullCardTest(){
        board.addCard(null);
    }

    @Test(expected = IllegalStateException.class)
    public void addSixCardTest(){
        Deck deck = new Deck();
        for(int i = 0; i < 6; i++){
            board.addCard(deck.deal());
        }
    }

    @Test
    public void boardTest(){
        board.clear();
        Deck deck = new Deck();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 5; i++){
            Card card = deck.deal();
            builder.append(card.getValue());
            board.addCard(card);
        }
        Assert.assertEquals(5,board.nbCard());
        Assert.assertEquals(builder.toString(),board.toString());
        board.clear();
        Assert.assertEquals(0,board.nbCard());
    }


}
