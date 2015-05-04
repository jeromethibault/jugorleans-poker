package fr.jugorleans.poker.client.event;

/**
 * Evénement propagé pour l'affichage de la table
 */
public class ShowTableEvent {

    /**
     * Identifiant du tournoi
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
