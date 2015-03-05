package fr.jugorleans.poker.server.game;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Combination;
import fr.jugorleans.poker.server.core.hand.CombinationComparator;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.spec.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * HandResolver pour le Holdem
 */
@Component
@Slf4j
public class DefaultCombinationResolver implements CombinationResolver {

    /**
     * La liste des spécifications
     */
    private static final HashMap<Combination, Class> SPECIFICATIONS = Maps.newHashMap();

    /**
     * Initialisation de la liste des spécifications
     */
    static {
        SPECIFICATIONS.put(Combination.STRAIGHT_FLUSH, StraightFlushSpecification.class);
        SPECIFICATIONS.put(Combination.FOUR_OF_KIND, FourOfKindSpecification.class);
        SPECIFICATIONS.put(Combination.FULL_HOUSE, FullHouseSpecification.class);
        SPECIFICATIONS.put(Combination.FLUSH, FlushSpecification.class);
        SPECIFICATIONS.put(Combination.STRAIGHT, StraightSpecification.class);
        SPECIFICATIONS.put(Combination.THREE_OF_KIND, ThreeOfKindSpecification.class);
        SPECIFICATIONS.put(Combination.TWO_PAIR, TwoPairSpecification.class);
        SPECIFICATIONS.put(Combination.PAIR, PairSpecification.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Combination resolve(Board board, Hand hand) {

        for (Combination combination : SPECIFICATIONS.keySet().stream().sorted(new CombinationComparator()).collect(Collectors.toList())) {
            Class specificationClass = SPECIFICATIONS.get(combination);
            try {
                Constructor constructor = specificationClass.getConstructor(Board.class);
                Specification<Hand> specification = (Specification) constructor.newInstance(board);
                if (specification.isSatisfiedBy(hand)) {
                    return combination;
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                log.error("Erreur lors de la résolution de la main", e);
            }
        }

        return Combination.HIGH;
    }
}
