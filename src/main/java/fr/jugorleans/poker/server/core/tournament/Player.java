package fr.jugorleans.poker.server.core.tournament;

import fr.jugorleans.poker.server.core.play.Action;
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
     * Siège attribué
     */
    private Seat seat;


}
