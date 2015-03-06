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
 * Le populator pour une combinaison de type "TWO_PAIR"
 */
public class TwoPairPopulator implements CombinationPopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.TWO_PAIR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        Map<CardValue, Long> counters = ListCard.countCardValue(list);
        CombinationStrength combinationStrength = null;
        for(CardValue cardValue : counters.keySet().stream().sorted((c1,c2) -> c2.getForce() - c1.getForce()).collect(Collectors.toList())){
            Long nb = counters.get(cardValue);
            if(nb == 2L){
                list.removeIf(card -> card.getCardValue().equals(cardValue));
                if(combinationStrength == null){
                    combinationStrength =  CombinationStrength.name(Combination.TWO_PAIR).of(cardValue);
                }else{
                    combinationStrength.and(cardValue).with(ListCard.orderDescByForce(list).get(0));
                    break;
                }
            }
        }
        return combinationStrength;
    }
}
