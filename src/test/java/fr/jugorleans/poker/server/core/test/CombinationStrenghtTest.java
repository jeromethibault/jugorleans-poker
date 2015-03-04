package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Combination;
import fr.jugorleans.poker.server.core.CombinationStrength;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.CombinationStrength}
 */
public class CombinationStrenghtTest {

    @Test
    public void test(){
        int hand1 = CombinationStrength.name(Combination.PAIR).of(CardValue.ACE).with(CardValue.KING).getStrength();
        int hand2 = CombinationStrength.name(Combination.PAIR).of(CardValue.ACE).with(CardValue.QUEEN).getStrength();
        Assert.assertTrue(hand1 > hand2);
    }

    @Test
    public void test2(){
        int hand1 = CombinationStrength.name(Combination.FLUSH).of(CardValue.ACE).getStrength();
        int hand2 = CombinationStrength.name(Combination.FLUSH).of(CardValue.KING).getStrength();
        Assert.assertTrue(hand1 > hand2);
    }
}
