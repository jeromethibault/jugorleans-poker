package fr.jugorleans.poker.server.core.hand;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * Après l'abbatage, une main est constitué d'une combinasion portant sur une carte principale
 * et d'éventuellement d'un kicker
 */
public class Strength {

    /**
     * Le coefficient pour la carte principale de la combination
     */
    private static final int COEFF_COMBINATION = 100000;

    /**
     * Le coefficient pour la seconde carte de la combination
     */
    private static final int COEFF_FIRST_VALUE = 10000;

    /**
     * Le coefficient pour la carte princpale
     */
    private static final int COEFF_SECOND_VALUE = 100;

    /**
     * Constructeur privée
     */
    private Strength() {

    }

    /**
     * Calculer la force d'une main
     *
     * @param combination la combinaison
     * @param mainCard    la carte "maitresse"
     * @param kicker      le kicker
     * @return la force de la main sous forme d'entier unique
     */
    public static int calculate(Combination combination, CardValue mainCard, Optional<CardValue> secondValue, Optional<CardValue> kicker) {
        Preconditions.checkArgument(mainCard != null);
        int secondValueForce = secondValue.isPresent() ? secondValue.get().getForce() : 0;
        int kickerForce = kicker.isPresent() ? kicker.get().getForce() : 0;
        return COEFF_COMBINATION * combination.getForce() + mainCard.getForce() * COEFF_FIRST_VALUE + secondValueForce * COEFF_SECOND_VALUE + kickerForce;
    }
}
