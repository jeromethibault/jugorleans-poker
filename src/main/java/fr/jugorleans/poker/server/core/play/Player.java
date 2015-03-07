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
     * Booléen indiquant si le joueur est éliminé du tournoi (out)
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
     * Check
     */
    public void check() {
        lastAction = Action.CHECK;
    }


    /**
     * Call
     *
     * @param callValue valeur de la mise à retirer du Stack
     */
    public void call(int callValue) {
        stack = stack - callValue;
        if (callValue == 0) {
            check();
        } else {
            lastAction = Action.CALL;
        }
    }

    /**
     * Mise initiale ou relance
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

    /**
     * Vérification que le joueur est foldé durant un Play
     *
     * @return vrai s'il est foldé, faux dans le cas contraire
     */
    public boolean isFolded() {
        return Action.FOLD.equals(lastAction);
    }

    /**
     * Encaissement des gains
     *
     * @param amount montant à ajouter au stack
     */
    public void win(int amount) {
        stack = stack + amount;
    }


}
