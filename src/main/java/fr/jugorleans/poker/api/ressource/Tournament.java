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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
