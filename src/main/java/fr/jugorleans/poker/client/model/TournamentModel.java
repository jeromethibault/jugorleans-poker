package fr.jugorleans.poker.client.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Objet Model du tableau des tournois
 */
public class TournamentModel {

    private SimpleStringProperty id = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
