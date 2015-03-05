package fr.jugorleans.poker.server.core.hand;

import fr.jugorleans.poker.server.util.HasValue;

/**
 * Enumération des différentes familles que peut prendre une carte
 */
public enum CardSuit implements HasValue {

    DIAMONDS {
        @Override
        public String getValue() {
            return "D";
        }
    },
    HEARTS {
        @Override
        public String getValue() {
            return "H";
        }
    },
    SPADES {
        @Override
        public String getValue() {
            return "S";
        }
    },
    CLUBS {
        @Override
        public String getValue() {
            return "C";
        }
    };

    @Override
    public String toString() {
        return getValue();
    }

}
