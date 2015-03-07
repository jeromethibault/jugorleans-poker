package fr.jugorleans.poker.server.game;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.populator.*;
import fr.jugorleans.poker.server.util.ListCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation par défaut de <code>HandStrenghtResolver</code>
 */
@Component
public class DefaultHandStrengthResolver implements HandStrengthResolver {

    /**
     * Résolution des combinaisons
     */
    @Autowired
    private CombinationResolver combinationResolver;

    /**
     * La liste des populators
     */
    private List<CombinationPopulator> populators = Lists.newArrayList();

    /**
     * Initialisation de la liste des populators
     */
    @PostConstruct
    public void init(){
        populators.add(new HightPopulator());
        populators.add(new PairPopulator());
        populators.add(new TwoPairPopulator());
        populators.add(new ThreeOfKindPopulator());
        populators.add(new StraightPopulator());
        populators.add(new FullHousePopulator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHandStrenght(Hand hand, Board board) {
        Combination combination = combinationResolver.resolve(board, hand);
        return populators.stream().filter(pop -> pop.handleCombination(combination)).map(pop -> pop.populate(ListCard.newArrayList(board, hand)).getStrength())
                .collect(Collectors.toList()).stream().mapToInt(i -> i).max().getAsInt();
    }
}
