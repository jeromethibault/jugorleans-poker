package fr.jugorleans.poker.client;


import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import fr.jugorleans.poker.client.event.AddPlayerEvent;
import fr.jugorleans.poker.client.event.TournamentCreatedEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Created by francoispenaud on 27/04/15.
 */
public class TableController {

    /**
     * TODO classe à externaliser dans un package model
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

    public class TournamentCreatedSubscriber{

        @Subscribe
        public void handleTournamentCreatedEvent(TournamentCreatedEvent event){
            System.out.println("Reception évènement TournamentCreated");
            tournamentId = event.getTournamentId();
        }
    }

    public class AddPlayerSubscriber{

        @Subscribe
        public void handleAddPlayerEvent(AddPlayerEvent event){
            System.out.println("Gestion de l'évènement addplayer");
            Player player = new Player();
            player.setNickname(event.getLogin());
            listPlayer.add(player);
        }
    }

    private void handleTransaction(){
        Controller.eventBus().register(new AddPlayerSubscriber());
        Controller.eventBus().register(new TournamentCreatedSubscriber());

    }

    public TableController(){
        handleTransaction();
    }

    @FXML
    private TableView leaders;

    @FXML
    private TextField playerName;

    @FXML
    private HBox rootLayer;

    private String tournamentId;

    private ObservableList<Player> listPlayer = FXCollections.observableArrayList();

    public void showTable() {
        TableColumn seatColumn = new TableColumn("Seat");
        seatColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("seat"));

        TableColumn nickNameColumn = new TableColumn("Player");
        nickNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("nickname"));

        leaders.getColumns().addAll(nickNameColumn,seatColumn);
        leaders.setItems(listPlayer);
        rootLayer.setVisible(true);
    }

    public void addPlayer(){
        Controller.tournamentApi().register(this.tournamentId, playerName.getText());
    }
}
