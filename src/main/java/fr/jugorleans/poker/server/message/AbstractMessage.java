package fr.jugorleans.poker.server.message;

/**
 *@author THIBAULT Jérôme
 */
public class AbstractMessage {

    /**
     * Le type de message
     */
    private String type;

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }
}
