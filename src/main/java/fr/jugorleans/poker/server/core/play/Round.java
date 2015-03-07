package fr.jugorleans.poker.server.core.play;

/**
 * Enumération des différents rounds d'une main
 */
public enum Round implements NextRound {

    PREFLOP {
        @Override
        public Round next() {
            return FLOP;
        }
    },
    FLOP {
        @Override
        public Round next() {
            return TURN;
        }

        @Override
        public int nbCardsToAddOnBoard() {
            return 3;
        }
    },
    TURN {
        @Override
        public Round next() {
            return RIVER;
        }

        @Override
        public int nbCardsToAddOnBoard() {
            return 1;
        }
    },
    RIVER {
        @Override
        public Round next() {
            return SHOWDOWN;
        }

        @Override
        public int nbCardsToAddOnBoard() {
            return 1;
        }
    },
    SHOWDOWN {
        @Override
        public Round next() {
            return null;
        }
    }
}
