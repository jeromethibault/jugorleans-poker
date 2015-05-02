package fr.jugorleans.poker.client.event;

/**
 * Evènement posté lors de la création d'un nouveau tournoi
 */
public class TournamentCreatedEvent {

    /**
     * Identifiant du tournoi
     */
    private String tournamentId;

    public TournamentCreatedEvent(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentId() {
        return tournamentId;
    }
}
