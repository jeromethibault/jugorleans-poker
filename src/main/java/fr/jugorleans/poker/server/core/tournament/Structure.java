package fr.jugorleans.poker.server.core.tournament;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by francoispenaud on 06/03/15.
 */
@Getter
@Builder
@ToString
public class Structure {

    /**
     * Liste des rounds dans l'ordre
     */
    private List<Blind> rounds;

    /**
     * Durée d'un round en minutes
     */
    private int duration;
}
