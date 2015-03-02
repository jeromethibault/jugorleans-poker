package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Combination;
import fr.jugorleans.poker.server.core.HandStrenght;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.HandStrenght}
 */
public class HandStrenghtTest {

    @Test
    public void testNotNull() {
        Optional<CardValue> kicker = Optional.of(CardValue.KING);
        Assert.assertNotNull(HandStrenght.getStrenght(Combination.PAIR, CardValue.ACE, Optional.of(CardValue.KING)));
    }

    @Test
    public void test1(){
        int hand1 = HandStrenght.getStrenght(Combination.PAIR, CardValue.ACE, Optional.of(CardValue.TEN));
        int hand2 = HandStrenght.getStrenght(Combination.PAIR, CardValue.ACE, Optional.of(CardValue.JACK));
        Assert.assertTrue(hand2 > hand1);
    }

    @Test
    public void test2(){
        int hand1 = HandStrenght.getStrenght(Combination.FLUSH, CardValue.KING, Optional.empty());
        int hand2 = HandStrenght.getStrenght(Combination.FLUSH, CardValue.QUEEN, Optional.empty());
        Assert.assertTrue(hand2 < hand1);
    }

    @Test
    public void test3(){
        int hand1 = HandStrenght.getStrenght(Combination.HIGH, CardValue.JACK, Optional.of(CardValue.TEN));
        int hand2 = HandStrenght.getStrenght(Combination.HIGH, CardValue.QUEEN, Optional.of(CardValue.NINE));
        Assert.assertTrue(hand2 > hand1);
    }

}