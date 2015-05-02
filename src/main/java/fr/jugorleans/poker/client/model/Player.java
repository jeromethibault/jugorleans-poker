package fr.jugorleans.poker.client.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Objet Model du tableau permettant la visualisation
 * des joueurs inscrit à un tournoi
 *
 * @author THIBAULT Jérôme
 */
public class Player {

    private SimpleStringProperty seat = new SimpleStringProperty();

    private SimpleStringProperty nickname = new SimpleStringProperty();

    public String getSeat() {
        return seat.get();
    }


    public void setSeat(String seat) {
        this.seat.set(seat);
    }

    public String getNickname() {
        return nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }
}
