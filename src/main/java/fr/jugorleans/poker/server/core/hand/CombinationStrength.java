package fr.jugorleans.poker.server.core.hand;

import java.util.Optional;

/**
 * Created by Jelie on 03/03/15.
 */
public class CombinationStrength {

    /**
     * La combination
     */
    private Combination name;

    /**
     * La carte principale de la combination
     */
    private CardValue firstValue;

    /**
     * La carte secondaire de la combination optionelle
     */
    private Optional<CardValue> secondValue = Optional.empty();

    /**
     * Le kicker
     * Il n'est pas obligatoire pour toute les combination;
     */
    private Optional<CardValue> kicker = Optional.empty();

    /**
     * Constructeur privé
     *
     * @param combination la combination
     */
    private CombinationStrength(Combination combination) {
        this.name = combination;
    }

    /**
     * Initialisation d'une nouvelle combinaison
     *
     * @param combination la combination
     * @return <code>CombinationStrenght</code>
     */
    public static CombinationStrength name(Combination combination) {
        return new CombinationStrength(combination);
    }

    /**
     * Assigner la carte principale
     *
     * @param cardValue la carte
     * @return <code>CombinationStrenght</code>
     */
    public CombinationStrength of(CardValue cardValue) {
        this.firstValue = cardValue;
        return this;
    }

    /**
     * Assigner le kicker
     *
     * @param cardValue la carte
     * @return <code>CombinationStrenght</code>
     */
    public CombinationStrength with(CardValue cardValue) {
        kicker = Optional.of(cardValue);
        return this;
    }

    /**
     * Assigner la carte secondaire
     *
     * @param cardValue la valeur de la carte
     * @return <code>CombinationStrenght</code>
     */
    public CombinationStrength and(CardValue cardValue){
        secondValue = Optional.of(cardValue);
        return this;
    }

    /**
     * @return la force de la combinason sous forme d'entier unique
     */
    public int getStrength() {
        return Strength.calculate(name, firstValue, secondValue, kicker);
    }
}
