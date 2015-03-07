package fr.jugorleans.poker.server.populator;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.CombinationStrength;
import fr.jugorleans.poker.server.util.ListCard;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Le populator pour une combinaison de type "STRAIGHT"
 */
public class StraightPopulator implements CombinationPopulator {

    /**
     * Nombre de carte nécessaire pour constituer une quinte
     */
    public static final int NB_STRAIGHT_CARD = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.STRAIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        CombinationStrength combinationStrength = CombinationStrength.name(Combination.STRAIGHT);
        List<CardValue> listCardValue = ListCard.orderAscByForce(list);

        int modulo = listCardValue.size() % NB_STRAIGHT_CARD;
        for (int i = 0; i <= modulo; i++) {
            int straight = listCardValue.get(4 + i).getForce() - listCardValue.get(i).getForce();
            if (straight == 4) {
                combinationStrength.of(listCardValue.get(4 + i));
            }
        }
        return combinationStrength;
    }
}
