package fr.jugorleans.poker.server.core.play;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

/**
 * Représente le pot
 */
@Getter
@Setter
@ToString
public class Pot {

    /**
     * le montant du pot
     */
    private Integer amount = 0;

    /**
     * La liste des side pots - TODO nécessité de garder les joueurs associés à un sidePot, donc Integer insuffisant
     */
    private Optional<List<Integer>> sidePot = Optional.empty();

    /**
     * Montant du round courant surlequel il faut s'aligner pour accéder au currentRound suivant
     */
    private Integer roundBet = 0;

    /**
     * Montant de la dernière relance
     */
    private Integer lastRaise = 0;

    /**
     * Ajout d'une mise dans le pot principal
     *
     * @param betValue montant de la mise
     */
    public void addToPot(int betValue) {
        amount = amount + betValue;
    }

    /**
     * Prise en compte d'un nouveau currentRound
     *
     * @param roundBet  montant roundbet (bb si début de partie, sinon 0)
     * @param lastRaise montant big blind (mise minimum)
     */
    public void newRound(int roundBet, int lastRaise) {
        this.roundBet = roundBet;
        this.lastRaise = lastRaise;
    }

}
