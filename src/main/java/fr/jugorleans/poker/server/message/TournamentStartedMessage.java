package fr.jugorleans.poker.server.message;

/**
 * Message envoyé lorsqu'un tournoi commence
 *
 * @author THIBAULT Jérôme
 */
public class TournamentStartedMessage extends AbstractMessage {

    /**
     * Identifiant du tournoi
     */
    private String id;

    public TournamentStartedMessage(){
        setType("tournamentStarted");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
