package fr.jugorleans.poker.server.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.util.HasValue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Représente le tableau c'est à dire les cartes visibles par tous les joueurs.
 * Le tableau est constitué du flop (3 cartes), de la turn (1 carte) et de la river (1 carte)
 */
public class Board implements HasValue {

    /**
     * Le tableau
     */
    private List<Card> board = Lists.newArrayList();


    /**
     * Ajouter une carte sur le board.
     * Le board ne peut pas accueillir plus de 5 cartes
     *
     * @param card la carte
     */
    public void addCard(Card card){
        Preconditions.checkArgument(card != null);
        Preconditions.checkState(!board.contains(card));
        Preconditions.checkState(board.size() < 5);
        board.add(card);
    }

    /**
     * La valeur du board est la concaténation de la valeur des cartes du board
     *
     * @return la valeur du board
     */
    @Override
    public String getValue() {
        StringBuilder builder = new StringBuilder();
        for(Card card : board){
            builder.append(card.getValue());
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    /**
     * @return le nombre de carte dans le board
     */
    public int nbCard(){
        return this.board.size();
    }

    /**
     * Retirer les cartes du board
     */
    public void clear(){
        this.board.clear();
    }

    /**
     * @return le nombre de carte unique sur le board
     */
    public int nbUniqueValueCard(){
        Set<CardValue> cardValue = new HashSet<>();
        this.board.stream().forEach(card -> cardValue.add(card.getCardValue()));
        return cardValue.size();
    }

    /**
     * @return la liste des cartes du board
     */
    public List<Card> getCards(){
        return this.board;
    }

    /**
     * Calculer le nombre de présence d'une valeur dans le board
     *
     * @param cardValue la valeur en question
     * @return le nombre d'apparition de la valeur
     */
    public int nb(CardValue cardValue){
        int nb = 0;
        for(Card card : this.board){
           if(card.getCardValue().equals(cardValue)){
               nb++;
           }
       }
        return nb;
   }
}
