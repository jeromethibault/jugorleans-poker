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
    },
    TURN {
        @Override
        public Round next() {
            return RIVER;
        }
    },
    RIVER {
        @Override
        public Round next() {
            return END;
        }
    },
    END {
        @Override
        public Round next() {
            return null;
        }
    }
}
