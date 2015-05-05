package fr.jugorleans.poker.api.ressource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ressource REST représentant un tournoi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tournament {

    /**
     * Identifiant du tournoi
     */
    private String id;

    /**
     * Status du tournoi
     */
    private boolean started;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
