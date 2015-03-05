package fr.jugorleans.poker.server.util;

import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe utilitaire autours des ListCard
 */
public final class ListCard {

    /**
     * Mettre dans une liste le contenu du board et de la main
     *
     * @param board le board
     * @param hand la main
     * @return L'ensemble des cartes sous forme de liste
     */
    public static List<Card> newArrayList(Board board, Hand hand){
        List<Card> list = com.google.common.collect.Lists.newArrayList(board.getCards());
        list.addAll(hand.getCards());
        return list;
    }

    /**
     * Trier une liste de carde par ordre croissant de leur force
     *
     * @param list la liste de carte
     * @return la liste triée
     */
    public static List<CardValue> orderAscByForce(List<Card> list){
        return list.stream().map(card -> card.getCardValue()).collect(Collectors.toSet())
                .stream().sorted((c1, c2) -> Integer.compare(c1.getForce(), c2.getForce())).collect(Collectors.toList());
    }

    /**
     * Trier une liste de carde par ordre décroissant de leur force
     *
     * @param list la liste de carte
     * @return la liste triée
     */
    public static List<CardValue> orderDescByForce(List<Card> list){
        return list.stream().map(card -> card.getCardValue()).collect(Collectors.toSet())
                .stream().sorted((c1, c2) -> Integer.compare(c2.getForce(), c1.getForce())).collect(Collectors.toList());
    }

    /**
     * Retourner sous forme de Map le nombre de chaque CardValue dans la liste
     *
     * @param list la liste de carte
     * @return la map
     */
    public static Map<CardValue,Long> countCardValue(List<Card> list){
        return list.stream().collect(Collectors.groupingBy(Card::getCardValue, Collectors.counting()));
    }
}
