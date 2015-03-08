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
 * Le populator pour une combinaison de type "FULL_HOUSE"
 */
public class FullHousePopulator implements CombinationPopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.FULL_HOUSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        Map<CardValue, Long> counters = ListCard.countCardValue(list);
        CombinationStrength combinationStrength = CombinationStrength.name(Combination.FULL_HOUSE);
        int populate = 0;
        boolean of = false;
        for(CardValue cardValue : counters.keySet().stream().sorted((c1,c2) -> c2.getForce() - c1.getForce()).collect(Collectors.toList())){
            Long nb = counters.get(cardValue);
            if(nb == 3L && !of){
                populate++;
                of = true;
                combinationStrength.of(cardValue);
            }else{
                if(nb == 2L || (nb == 3L && of)){
                    populate++;
                    combinationStrength.and(cardValue);
                }
            }
            if(populate == 2){
                break;
            }
        }
        return combinationStrength;
    }
}
