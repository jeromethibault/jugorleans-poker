package fr.jugorleans.poker.server.populator;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.CombinationStrength;
import fr.jugorleans.poker.server.util.ListCard;

import java.util.List;
import java.util.Map;

/**
 * Le populator pour une combinaison de type "PAIR"
 */
public class PairPopulator implements CombinationPopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.PAIR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        Map<CardValue, Long> counters = ListCard.countCardValue(list);
        CombinationStrength combinationStrength = null;
        for(CardValue cardValue : counters.keySet()){
            Long nb = counters.get(cardValue);
            if(nb == 2L){
                list.removeIf(card -> card.getCardValue().equals(cardValue));
                combinationStrength =  CombinationStrength.name(Combination.PAIR).of(cardValue).with(ListCard.orderDescByForce(list).get(0));
                break;
            }
        }
        return combinationStrength;
    }
}
