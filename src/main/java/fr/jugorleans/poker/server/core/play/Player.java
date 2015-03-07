package fr.jugorleans.poker.server.core.play;

import fr.jugorleans.poker.server.core.hand.Hand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Joueur
 */
@Getter
@Setter
@Builder
@ToString
public class Player {

    /**
     * Surnom du joueur
     */
    private String nickName;

    /**
     * Stack courant mis à jour après chaque Play
     */
    private Integer stack;

    /**
     * Booléen indiquant si le joueur est éliminé (out)
     */
    private boolean out = false;

    /**
     * Dernière action
     */
    private Action lastAction;

    /**
     * Main (2 cartes) en cours
     */
    private Hand currentHand;

    /**
     * Siège attribué
     */
    private Seat seat;


    /**
     * Mise du joueur
     *
     * @param betValue valeur de la mise à retirer du stack
     */
    public void bet(int betValue) {
        stack = stack - betValue;
        lastAction = Action.BET;
    }

    /**
     * Fold
     */
    public void fold() {
        lastAction = Action.FOLD;
    }
}
