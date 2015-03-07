package fr.jugorleans.poker.server.core.play;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Siège
 */
@Getter
@Setter
@ToString
@Builder
public class Seat {

    /** Numero du siege */
    private int number;
}
