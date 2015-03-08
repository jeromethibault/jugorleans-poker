package fr.jugorleans.poker.server.populator;

import fr.jugorleans.poker.server.core.hand.*;
import fr.jugorleans.poker.server.util.ListCard;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Le populator pour une combinaison de type "FLUSH"
 */
public class FlushPopulator implements CombinationPopulator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleCombination(Combination combination) {
        return combination.equals(Combination.FLUSH);
    }

    /**
     * Retourner la carte max d'une famille
     *
     * @param cardSuit la famille
     * @param list la liste de carte
     * @return la carte max
     */
    private CardValue getMaxCardValue(CardSuit cardSuit,List<Card> list){
        return list.stream().filter(card -> card.getCardSuit().equals(cardSuit))
                .max((card1,card2) -> card1.getCardValue().getForce() - card2.getCardValue().getForce()).get().getCardValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombinationStrength populate(List<Card> list) {
        CombinationStrength combinationStrength = CombinationStrength.name(Combination.FLUSH);
        Map<CardSuit, Long> map = ListCard.countCardSuit(list);

        for(CardSuit cardSuit : map.keySet()){
            long nb = map.get(cardSuit);
            if(nb >= 5L){
                combinationStrength.of(getMaxCardValue(cardSuit,list));
                break;
            }
        }

        return combinationStrength;
    }
}
