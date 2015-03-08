package fr.jugorleans.poker.server.core.play;

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

    /**
     * Ante
     */
    private int ante;

    /**
     * Vérification de la validité des blinds
     *
     * @return vrai si les valeurs sont correctes, faux dans le cas contraire
     */
    public boolean isValid() {
        return smallBlind < bigBlind && smallBlind > 0 && bigBlind > 0 && ante >= 0;
    }
}
