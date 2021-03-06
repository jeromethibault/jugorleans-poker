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
     * Booléen permettant de savoir si le joueur est allin
     */
    private boolean isAllIn = false;

    /**
     * Classement
     */
    private int rank;

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

        checkIsAllIn();
    }


    /**
     * Mise initiale ou relance
     *
     * @param betValue valeur de la mise à retirer du stack
     */
    public void bet(int betValue) {
        stack = stack - betValue;
        lastAction = Action.BET;
        checkIsAllIn();
    }

    /**
     * Fold
     */
    public void fold() {
        lastAction = Action.FOLD;
    }

    /**
     * Paiement de la small blind
     *
     * @param sbValue montant de la SB
     */
    public void paySmallBlind(int sbValue) {
        stack = stack - sbValue;
        checkIsAllIn();
    }

    /**
     * Paiement de la big blind
     *
     * @param bbValue montant de la BB
     */
    public void payBigBlind(int bbValue) {
        stack = stack - bbValue;
    }

    /**
     * Paiement d'une ante
     *
     * @param anteValue montant de la BB
     */
    public void payAnte(int anteValue) {
        stack = stack - anteValue;
        checkIsAllIn();
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

    /**
     * Vérification que le joueur est encore actif à table (pas foldé, pas allin)
     * @return vrai s'il n'est ni allin ni foldé, faux dans le cas contraire
     */
    public boolean canPlay(){
        return !isFolded() && !isAllIn();
    }
    /**
     * Vérification que le joueur n'est pas éliminé
     */
    public void checkIsOut(){
        if (stack == 0){
            out = true;
        }
    }

    /**
     * Vérification que le joueur n'est pas automatiquement allin
     */
    private void checkIsAllIn() {
        if (stack == 0) {
            isAllIn = true;
            lastAction = Action.ALL_IN;
        }
    }



}
