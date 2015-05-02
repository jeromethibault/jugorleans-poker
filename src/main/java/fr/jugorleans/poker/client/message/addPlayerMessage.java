package fr.jugorleans.poker.client.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objet représentant un message lors de la réception d'un message
 * de type "addPlayer"
 *
 * @author THIBAULT Jérôme
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class addPlayerMessage {

    /**
     * Le pseudo du joueur
     */
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
