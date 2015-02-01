package fr.jugorleans.poker.server.core;

import com.google.common.base.Preconditions;
import fr.jugorleans.poker.server.util.HasValue;

/**
 * Représente une main. Une main est composé de deux cartes
 */
public class Hand implements HasValue{

    /**
     * La première carte
     */
    private Card firstCard;

    /**
     * La seconde carte
     */
    private Card secondCard;

    /**
     * Construire une main avec l'aide du builder
     */
    private Hand(Builder builder){
        this.firstCard = builder.firstCard;
        this.secondCard = builder.secondCard;
    }

    public Card getFirstCard() {
        return firstCard;
    }

    public Card getSecondCard() {
        return secondCard;
    }

    /**
     * Construire une nouvelle main
     */
    public static class Builder{

        private Card firstCard;

        private Card secondCard;

        /**
         * Assigner la permière carte
         *
         * @param card la carte
         * @return l'instance du builder
         */
        public Builder firstCard(Card card){
            Preconditions.checkArgument(card != null);
            this.firstCard = card;
            return this;
        }


        /**
         * Assigner la première carte
         *
         * @param value la valeur de la carte
         * @param suit la famille de la carte
         * @return l'instance du builder
         */
        public Builder firstCard(CardValue value, CardSuit suit){
            this.firstCard = Card.newBuilder().value(value).suit(suit).build();
            return this;
        }

        /**
         * Assigner la seconde carte
         *
         * @param card la carte
         * @return l'instance du builder
         */
        public Builder secondCard(Card card){
            Preconditions.checkArgument(card != null);
            this.secondCard = card;
            return this;
        }

        /**
         * Assigner la seconde carte
         *
         * @param value la valeur de la carte
         * @param suit la famille de la carte
         * @return l'instance du builder
         */
        public Builder secondCard(CardValue value, CardSuit suit){
            this.secondCard = Card.newBuilder().value(value).suit(suit).build();
            return this;
        }

        /**
         * @return la main
         */
        public Hand build(){
            Preconditions.checkState(!firstCard.equals(secondCard));
            return new Hand(this);
        }
    }

    /**
     * @return une instance du builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    /**
     * La valeur d'une main est la combinaison de la valeur des deux
     * cartes
     *
     * @return la valeur de la main
     */
    @Override
    public String getValue() {
        return firstCard.getValue()+secondCard.getValue();
    }

    /**
     * @return vrai si les deux cartes sont de la même famille. Faux sinon.
     */
    public boolean isSuited(){
        return this.firstCard.getCardSuit().equals(this.secondCard.getCardSuit());

    }

    /**
     * @return vrai si la main est une paire servie. Faux sinon
     */
    public boolean isPocket(){
        return this.firstCard.getCardValue().equals(this.secondCard.getCardValue());
    }
}
