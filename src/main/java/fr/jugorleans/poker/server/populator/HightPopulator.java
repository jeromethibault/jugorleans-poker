package fr.jugorleans.poker.server.populator;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.CombinationStrength;
import fr.jugorleans.poker.server.util.ListCard;

import java.util.List;

/**
 * Le populator pour une combinaison de type "HIGH"
 */
public class HightPopulator implements CombinationPopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.HIGH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        List<CardValue> cardValueList = ListCard.orderDescByForce(list);
        return CombinationStrength.name(Combination.HIGH).of(cardValueList.get(0)).with(cardValueList.get(1));
    }
}
