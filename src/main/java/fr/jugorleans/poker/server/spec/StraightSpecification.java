package fr.jugorleans.poker.server.spec;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Card;
import fr.jugorleans.poker.server.core.CardValue;
import fr.jugorleans.poker.server.core.Hand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Specification permettant d'évaluer si la main et le board constituent une suite
 */
public class StraightSpecification implements Specification<Hand> {

    /**
     * Le board
     */
    private final Board board;

    /**
     * Construire un {@link fr.jugorleans.poker.server.spec.StraightSpecification} sur un board donné
     *
     * @param board le board
     */
    public StraightSpecification(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(final Hand hand) {
        List<Card> listCard = Lists.newArrayList(this.board.getCards());
        listCard.addAll(hand.getCards());

        /*Trier les cartes via leur force par ordre croissant*/
        List<CardValue> setValue = listCard.stream().map(card -> card.getCardValue()).collect(Collectors.toSet())
                .stream().sorted((c1,c2) -> Integer.compare(c1.getForce(),c2.getForce())).collect(Collectors.toList());

        if(setValue.size() >= 5){
            int modulo = setValue.size()%5;
            for(int i=0;i<modulo+1;i++){
                int straight = setValue.get(4+i).getForce() - setValue.get(i).getForce();
                if(straight == 4){
                    return true;
                }
            }
        }

        return false;
    }
}