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

    /** Montant du currentRound courant surlequel il faut s'aligner pour accéder au currentRound suivant */
    private Integer roundBet = 0;

    /** Montan de la dernière relance */
    private Integer lastRaise = 0;

    /**
     * Ajout d'une mise dans le pot principal
     * @param betValue montant de la mise
     */
    public void addToPot(int betValue){
        amount = amount + betValue;
    }

    /**
     * Prise en compte d'un nouveau currentRound
     * @param bigBling montant big blind (mise minimum)
     */
    public void newRound(int bigBling){
        roundBet = 0;
        lastRaise = bigBling;
    }

}
