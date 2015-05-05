package fr.jugorleans.poker.client.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objet représentant le message lors de la réception
 * d'un message de type "tournamentStarted"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TournamentStartedMessage {

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
