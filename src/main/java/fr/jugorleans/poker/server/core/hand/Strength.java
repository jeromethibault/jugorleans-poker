package fr.jugorleans.poker.server.core.hand;

import java.util.Optional;

/**
 * Après l'abbatage, une main est constitué d'une combinasion portant sur une carte principale
 * et d'éventuellement d'un kicker
 */
public class Strength {

    /**
     * Le coefficient pour la combinaison
     */
    private static final int COEFF_COMBINATION = 10000;

    /**
     * Le coefficient pour la carte princpale
     */
    private static final int COEFF_MAIN_CARD = 100;

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
    public static int calculate(Combination combination, CardValue mainCard, Optional<CardValue> kicker) {
        int kickerForce = kicker.isPresent() ? kicker.get().getForce() : 0;
        return COEFF_COMBINATION * combination.getForce() + mainCard.getForce() * COEFF_MAIN_CARD + kickerForce;
    }
}
