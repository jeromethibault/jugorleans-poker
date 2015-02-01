package fr.jugorleans.poker.server.util;

/**
 * Représente un objet possédant une valeur de type <code>String</code>
 */
@FunctionalInterface
public interface HasValue {

    /**
     * @return la valeur
     */
    String getValue();
}
