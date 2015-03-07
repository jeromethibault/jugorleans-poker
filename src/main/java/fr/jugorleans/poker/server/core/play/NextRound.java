package fr.jugorleans.poker.server.core.play;

/**
 * Détermination du Round suivant
 */
public interface NextRound {

    /**
     * Round suivant
     * @return le retour suivant le round courant
     */
    Round next();

    /**
     * Nombre de cartes à ajouter au board
     * @return le nombre de cartes
     */
    default int nbCardsToAddOnBoard(){
        return 0;
    }

}
