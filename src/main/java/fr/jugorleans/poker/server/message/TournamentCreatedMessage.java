package fr.jugorleans.poker.server.message;

/**
 * Message envoyé lors de la création d'un nouveau tournoi
 *
 * @author THIBAULT Jérôme
 */
public class TournamentCreatedMessage extends AbstractMessage {

    /**
     * Identifiant du tournoi
     */
    private String id;

    public TournamentCreatedMessage(){
        setType("tournamentCreated");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
