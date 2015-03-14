package fr.jugorleans.poker.server.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire de manipulation des identifiants
 */
public final class Identification {

    /**
     * Separateur entre id tournament, id table et id play
     */
    public static final String ID_SEPARATOR = "_";

    /**
     * Récupération de l'idTable depuis l'idPlay
     *
     * @param idPlay identifiant d'un Play
     * @return l'id de la table associée
     */
    public static String getIdTableFromIdPlay(String idPlay) {
        return StringUtils.substringBeforeLast(idPlay, ID_SEPARATOR);
    }
}
