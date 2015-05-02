package fr.jugorleans.poker.client.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objet représentant le type d'un message
 *
 * @author THIBAULT Jérôme
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageType {

    /**
     * Le type de message
     *
     * TODO Faire une enum
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
