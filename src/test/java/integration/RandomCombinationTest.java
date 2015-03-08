package integration;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.play.Deck;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test qui va générer 1000 board et 1000 hand et lançer la résolution de chaque combinaison.
 *
 * C'est d'avantage un test de robustesse que d'intégration
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
@Slf4j
public class RandomCombinationTest {

    @Autowired
    private DefaultStrongestHandResolver defaultStrongestHandResolver;

    private Deck deck = new Deck();

    @Test
    public void test(){
        for(int i=0;i<1000;i++){
            deck.shuffleUp();
            Board board = new Board();
            board.addCards(deck.deal(5));

            Hand hand = Hand.newBuilder().firstCard(deck.deal(1).get(0)).secondCard(deck.deal(1).get(0)).build();

            log.debug("****** New Play *******");
            log.debug("Board => "+board.getValue());
            log.debug("Hand => "+hand.getValue());

            List<Hand> list = defaultStrongestHandResolver.getWinningHand(board, Lists.newArrayList(hand));
            Assert.assertTrue(list.size() != 0);
        }
    }
}
