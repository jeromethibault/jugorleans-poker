package fr.jugorleans.poker.api.ressource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ressource REST représentant un joueur
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    /**
     * Surnom du joueur
     */
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
