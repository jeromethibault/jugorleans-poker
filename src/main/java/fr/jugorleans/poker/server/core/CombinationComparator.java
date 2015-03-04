package fr.jugorleans.poker.server.core;

import java.util.Comparator;

/**
 * Comparator de Combinaison en fonction de la force avant de traiter en premier les meilleures combinaisons
 */
public class CombinationComparator implements Comparator<Combination> {
    @Override
    public int compare(Combination o1, Combination o2) {
        return o2.getForce() - o1.getForce();
    }
}
