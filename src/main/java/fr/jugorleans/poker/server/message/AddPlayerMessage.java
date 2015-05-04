package fr.jugorleans.poker.server.message;

/**
 * Message envoyé lors de l'ajout d'un joueur dans une partie
 *
 * @author THIBAULT Jérôme
 */
public class AddPlayerMessage extends AbstractMessage {

    /**
     * Le pseudo du joueur
     */
    private String nickname;

    /**
     * Identifiant du tournoi
     */
    private String idTournament;

    public AddPlayerMessage(){
        setType("addPlayer");
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(String idTournament) {
        this.idTournament = idTournament;
    }
}
