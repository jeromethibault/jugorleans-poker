package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Combination;
import fr.jugorleans.poker.server.core.CombinationStrenght;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.CombinationStrenght}
 */
public class CombinationStrenghtTest {

    @Test
    public void test(){
        int hand1 = CombinationStrenght.name(Combination.PAIR).of(CardValue.ACE).with(CardValue.KING).getStrenght();
        int hand2 = CombinationStrenght.name(Combination.PAIR).of(CardValue.ACE).with(CardValue.QUEEN).getStrenght();
        Assert.assertTrue(hand1 > hand2);
    }

    @Test
    public void test2(){
        int hand1 = CombinationStrenght.name(Combination.FLUSH).of(CardValue.ACE).getStrenght();
        int hand2 = CombinationStrenght.name(Combination.FLUSH).of(CardValue.KING).getStrenght();
        Assert.assertTrue(hand1 > hand2);
    }
}
