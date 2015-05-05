package fr.jugorleans.poker.client.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Objet Model du tableau des tournois
 */
public class TournamentModel {

    private SimpleStringProperty id = new SimpleStringProperty();

    private SimpleBooleanProperty started = new SimpleBooleanProperty();

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public boolean getStarted() {
        return started.get();
    }

    public SimpleBooleanProperty startedProperty() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started.set(started);
    }
}
