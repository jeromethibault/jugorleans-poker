package fr.jugorleans.poker.server.game;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation par defaut de <code>StrongestHandResolver</code>
 */
@Component
public class DefaultStrongestHandResolver implements StrongestHandResolver {

    /**
     * Composant qui calcule la force d'une main
     */
    @Autowired
    private HandStrengthResolver handStrengthResolver;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Hand> getWinningHand(Board board, List<Hand> listHand) {
        List<Integer> listStrength = listHand.stream().map(hand -> handStrengthResolver.getHandStrenght(hand, board)).collect(Collectors.toList());
        int winnningHand = listStrength.stream().mapToInt(i -> i).max().getAsInt();
        final List<Hand> listWinningHand = Lists.newArrayList();
        listStrength.stream().forEach(i -> {
            if(i.equals(winnningHand)){
                listWinningHand.add(listHand.get(listStrength.indexOf(i)));
            }
        });
        return listWinningHand;
    }
}
