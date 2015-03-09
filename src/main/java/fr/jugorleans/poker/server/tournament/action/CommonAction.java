package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;

/**
 * Partie commune à certaines actions
 */
public abstract class CommonAction {

    /**
     * Vérification que le joueur n'est pas allin sur sa nouvelle mise
     *
     * @param player joueur concerné
     * @param bet    mise
     * @return le montant du bet initial si le stack du joueur est différent, sinon le montant du tapis du joueur
     */
    protected int checkIsAllIn(Player player, int bet) {
        int playerStack = player.getStack();
        if (bet >= playerStack) {
            player.setAllIn(true);
            bet = playerStack;
        }

        return bet;
    }
}
