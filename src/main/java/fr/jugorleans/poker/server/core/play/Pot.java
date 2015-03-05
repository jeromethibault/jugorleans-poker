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


}
