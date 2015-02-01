package fr.jugorleans.poker.server.core;

import com.google.common.base.Objects;
import fr.jugorleans.poker.server.util.HasValue;

/**
 * Représente une carte. Une carte possède une valeur et une famille
 */
public class Card implements HasValue{

    /**
     * La valeur de la carte
     */
    private CardValue cardValue;

    /**
     * La famille de la carte
     */
    private CardSuit cardSuit;

    /**
     * Construire une nouvelle carte
     */
    private Card(Builder builder){
        this.cardValue = builder.value;
        this.cardSuit = builder.suit;
    }

    public String getCardValue() {
        return cardValue.getValue();
    }

    public String getCardSuit() {
        return cardSuit.getValue();
    }

    /**
     * La valeur d'une carte est la concaténation de la valeur et de la famille.
     * AS => As de pique ...
     *
     * @return la valeur de carte.
     */
    @Override
    public String getValue() {
        return cardValue.getValue()+cardSuit.getValue();
    }

    /**
     * @return une instance du builder de carte
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    /**
     * Le builder de carte
     */
    public static class Builder{

        private CardValue value;

        private CardSuit suit;

        /**
         * Assigner une valeur à la carte
         *
         * @param value la valeur de la carte
         * @return le builder
         */
        public Builder value(CardValue value){
            this.value = value;
            return this;
        }

        /**
         * Assigner une famille à la carte
         *
         * @param suit la famille de la carte
         * @return le builder
         */
        public Builder suit(CardSuit suit){
            this.suit = suit;
            return this;
        }

        /**
         * @return la carte
         */
        public Card build(){
            return new Card(this);
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            Card card = (Card)obj;
            return Objects.equal(this.getValue(),card.getValue());
        }
        return false;
    }
}
