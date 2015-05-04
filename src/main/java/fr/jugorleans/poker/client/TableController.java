package fr.jugorleans.poker.client;


import com.google.common.eventbus.Subscribe;
import fr.jugorleans.poker.client.event.AddPlayerInTournamentEvent;
import fr.jugorleans.poker.client.event.ShowTableEvent;
import fr.jugorleans.poker.client.event.TournamentCreatedEvent;
import fr.jugorleans.poker.client.model.PlayerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by francoispenaud on 27/04/15.
 */
public class TableController implements Initializable {

    /**
     * Handler de l'évènement {@link fr.jugorleans.poker.client.event.TournamentCreatedEvent}
     */
    public class TournamentCreatedSubscriber {

        @Subscribe
        public void handleTournamentCreatedEvent(TournamentCreatedEvent event){
            System.out.println("Reception évènement TournamentCreated");
            tournamentId = event.getTournamentId();
        }
    }

    /**
     * handler de l'évènement {@link fr.jugorleans.poker.client.event.AddPlayerInTournamentEvent}
     */
    public class AddPlayerSubscriber{

        @Subscribe
        public void handleAddPlayerEvent(AddPlayerInTournamentEvent event){
            System.out.println("Gestion de l'évènement addplayer");
            PlayerModel playerModel = new PlayerModel();
            playerModel.setNickname(event.getLogin());
            listPlayerModel.add(playerModel);
        }
    }

    /**
     * handler de l'évènement {@link fr.jugorleans.poker.client.event.ShowTableEvent}
     */
    public class ShowTableEventSubscriber{

        @Subscribe
        public void handleShowTableEvent(ShowTableEvent event){
            showTable();
        }

    }

    /**
     * Enregister les event handler dans le bus d'évènement
     */
    private void handleTransaction(){
        Controller.eventBus().register(new ShowTableEventSubscriber());
        Controller.eventBus().register(new AddPlayerSubscriber());
        Controller.eventBus().register(new TournamentCreatedSubscriber());

    }

    /**
     * Constructeur par défaut
     */
    public TableController(){
        handleTransaction();
    }

    @FXML
    private TableView leaders;

    @FXML
    private TextField playerName;

    @FXML
    private HBox rootLayer;

    /**
     * L'identifiant du tournoi
     */
    private String tournamentId;

    /**
     * La liste des joueurs inscrit au tournoi
     */
    private ObservableList<PlayerModel> listPlayerModel = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn seatColumn = new TableColumn("Seat");
        seatColumn.setCellValueFactory(new PropertyValueFactory<PlayerModel, String>("seat"));

        TableColumn nickNameColumn = new TableColumn("Player");
        nickNameColumn.setCellValueFactory(new PropertyValueFactory<PlayerModel, String>("nickname"));

        leaders.getColumns().addAll(nickNameColumn,seatColumn);
        leaders.setItems(listPlayerModel);
    }

    private void showTable() {
        rootLayer.setVisible(true);
    }

    public void addPlayer(){
        Controller.tournamentApi().register(this.tournamentId, playerName.getText());
    }
}
