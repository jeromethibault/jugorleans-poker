package fr.jugorleans.poker.server.populator;

import fr.jugorleans.poker.server.core.Card;
import fr.jugorleans.poker.server.core.Combination;
import fr.jugorleans.poker.server.core.CombinationStrength;

import java.util.List;

/**
 * PopulateCombination interface
 */
public interface CombinationPopulator {

    /**
     * @param combination la combinaison
     * @return True si le populator prend en charge la combinaison passée en paramètre
     */
    boolean handleCombination(Combination combination);

    /**
     * Compléter la combinaison avec la carte principale et éventuellement le kicker
     *
     * @param list Le board + la main
     * @return la combinaison complétée
     */
    CombinationStrength populate(List<Card> list);

}
