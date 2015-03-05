package fr.jugorleans.poker.server.core.hand;

import fr.jugorleans.poker.server.util.HasForce;
import fr.jugorleans.poker.server.util.HasValue;

/**
 * Enumération des différentes valeurs que peut prendre une carte
 */
public enum CardValue implements HasValue, HasForce {

    TWO {
        @Override
        public String getValue() {
            return "2";
        }

        @Override
        public int getForce() {
            return 1;
        }
    },
    THREE {
        @Override
        public String getValue() {
            return "3";
        }

        @Override
        public int getForce() {
            return 2;
        }
    },
    FOUR {
        @Override
        public String getValue() {
            return "4";
        }

        @Override
        public int getForce() {
            return 3;
        }
    },
    FIVE {
        @Override
        public String getValue() {
            return "5";
        }

        @Override
        public int getForce() {
            return 4;
        }
    },
    SIX {
        @Override
        public String getValue() {
            return "6";
        }

        @Override
        public int getForce() {
            return 5;
        }
    },
    SEVEN {
        @Override
        public String getValue() {
            return "7";
        }

        @Override
        public int getForce() {
            return 6;
        }
    },
    EIGHT {
        @Override
        public String getValue() {
            return "8";
        }

        @Override
        public int getForce() {
            return 7;
        }
    },
    NINE {
        @Override
        public String getValue() {
            return "9";
        }

        @Override
        public int getForce() {
            return 8;
        }
    },
    TEN {
        @Override
        public String getValue() {
            return "10";
        }

        @Override
        public int getForce() {
            return 9;
        }
    },
    JACK {
        @Override
        public String getValue() {
            return "J";
        }

        @Override
        public int getForce() {
            return 10;
        }
    },
    QUEEN {
        @Override
        public String getValue() {
            return "Q";
        }

        @Override
        public int getForce() {
            return 11;
        }
    },
    KING {
        @Override
        public String getValue() {
            return "K";
        }

        @Override
        public int getForce() {
            return 12;
        }
    },
    ACE {
        @Override
        public String getValue() {
            return "A";
        }

        @Override
        public int getForce() {
            return 13;
        }
    };

    @Override
    public String toString() {
        return getValue();
    }
}
