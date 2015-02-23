package fr.jugorleans.poker.server.core;

import fr.jugorleans.poker.server.util.HasForce;

/**
 * Enumération des différente combinaison et de leur force
 */
public enum Combination implements HasForce {

    HIGH {
        @Override
        public int getForce() {
            return 1;
        }
    },
    PAIR {
        @Override
        public int getForce() {
            return 2;
        }
    },
    TWO_PAIR {
        @Override
        public int getForce() {
            return 3;
        }
    },
    THREE_OF_KIND {
        @Override
        public int getForce() {
            return 4;
        }
    },
    STRAIGHT {
        @Override
        public int getForce() {
            return 5;
        }
    },
    FLUSH {
        @Override
        public int getForce() {
            return 6;
        }
    },
    FULL_HOUSE {
        @Override
        public int getForce() {
            return 7;
        }
    },
    FOUR_OF_KIND {
        @Override
        public int getForce() {
            return 8;
        }
    },
    STRAIGHT_FLUSH {
        @Override
        public int getForce() {
            return 9;
        }
    }
}
