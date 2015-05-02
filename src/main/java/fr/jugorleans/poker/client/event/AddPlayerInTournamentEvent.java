package fr.jugorleans.poker.client.event;

/**
 * Evenement lançé lors de l'inscription d'un joueur au tournoi
 *
 * @author THIBAULT Jérôme
 */
public class AddPlayerInTournamentEvent {

    private String login;

    public AddPlayerInTournamentEvent(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

}
