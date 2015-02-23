package fr.jugorleans.poker.server.core;

import fr.jugorleans.poker.server.util.HasValue;

/**
 * Enumération des différentes valeurs que peut prendre une carte
 */
public enum CardValue implements HasValue {

    TWO {
        @Override
        public String getValue() {
            return "2";
        }
    },
    THREE {
        @Override
        public String getValue() {
            return "3";
        }
    },
    FOUR {
        @Override
        public String getValue() {
            return "4";
        }
    },
    FIVE {
        @Override
        public String getValue() {
            return "5";
        }
    },
    SIX {
        @Override
        public String getValue() {
            return "6";
        }
    },
    SEVEN {
        @Override
        public String getValue() {
            return "7";
        }
    },
    EIGHT {
        @Override
        public String getValue() {
            return "8";
        }
    },
    NINE {
        @Override
        public String getValue() {
            return "9";
        }
    },
    TEN {
        @Override
        public String getValue() {
            return "10";
        }
    },
    JACK {
        @Override
        public String getValue() {
            return "J";
        }
    },
    QUEEN {
        @Override
        public String getValue() {
            return "Q";
        }
    },
    KING {
        @Override
        public String getValue() {
            return "K";
        }
    },
    ACE {
        @Override
        public String getValue() {
            return "A";
        }
    };

    @Override
    public String toString() {
        return getValue();
    }
}
