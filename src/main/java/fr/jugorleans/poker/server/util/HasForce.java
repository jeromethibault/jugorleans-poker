package fr.jugorleans.poker.server.util;

/**
 * Représente un object possédant un poid / une force
 */
@FunctionalInterface
public interface HasForce {

    /**
     * @return la force
     */
    int getForce();
}
