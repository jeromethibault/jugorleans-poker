package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.Strength;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.hand.Strength}
 */
public class StrenghtTest {

    @Test
    public void testNotNull() {
        Optional<CardValue> kicker = Optional.of(CardValue.KING);
        Assert.assertNotNull(Strength.calculate(Combination.PAIR, CardValue.ACE, Optional.empty(), Optional.of(CardValue.KING)));
    }

    @Test
    public void test1(){
        int hand1 = Strength.calculate(Combination.PAIR, CardValue.ACE,Optional.empty(), Optional.of(CardValue.TEN));
        int hand2 = Strength.calculate(Combination.PAIR, CardValue.ACE,Optional.empty(), Optional.of(CardValue.JACK));
        Assert.assertTrue(hand2 > hand1);
    }

    @Test
    public void test2(){
        int hand1 = Strength.calculate(Combination.FLUSH, CardValue.KING,Optional.empty(), Optional.empty());
        int hand2 = Strength.calculate(Combination.FLUSH, CardValue.QUEEN,Optional.empty(), Optional.empty());
        Assert.assertTrue(hand2 < hand1);
    }

    @Test
    public void test3(){
        int hand1 = Strength.calculate(Combination.HIGH, CardValue.JACK,Optional.empty(), Optional.of(CardValue.TEN));
        int hand2 = Strength.calculate(Combination.HIGH, CardValue.QUEEN,Optional.empty(), Optional.of(CardValue.NINE));
        Assert.assertTrue(hand2 > hand1);
    }

    @Test
    public void test4(){
        int hand1 = Strength.calculate(Combination.TWO_PAIR, CardValue.JACK,Optional.of(CardValue.NINE), Optional.of(CardValue.TEN));
        int hand2 = Strength.calculate(Combination.TWO_PAIR, CardValue.JACK,Optional.of(CardValue.EIGHT), Optional.of(CardValue.NINE));
        Assert.assertTrue(hand1 > hand2);
    }

}