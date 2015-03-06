package fr.jugorleans.poker.server.core.tournament;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Blinds
 */
@Getter
@ToString
@Builder
public class Blind {

    /**
     * SB
     */
    private int smallBlind;

    /**
     * BB
     */
    private int bigBlind;
}
