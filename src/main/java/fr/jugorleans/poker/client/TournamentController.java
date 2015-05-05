package fr.jugorleans.poker.client;

import com.google.common.eventbus.Subscribe;
import fr.jugorleans.poker.client.event.ShowHomeEvent;
import fr.jugorleans.poker.client.event.ShowTableEvent;
import fr.jugorleans.poker.client.event.TournamentCreatedEvent;
import fr.jugorleans.poker.client.message.TournamentCreatedMessage;
import fr.jugorleans.poker.client.message.TournamentStartedMessage;
import fr.jugorleans.poker.client.model.PlayerModel;
import fr.jugorleans.poker.client.model.TournamentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TournamentController implements Initializable{

    @FXML
    private TableView tounamentList;

    @FXML
    private VBox tournamentPane;

    public class TournamentCreatedMessageSubscriber{

        @Subscribe
        public void handleTournamentCreatedMessage(TournamentCreatedMessage message){
            TournamentModel model = new TournamentModel();
            model.setId(message.getId());
            listTournamentModel.addAll(model);
        }
    }

    public class ShowHomeEventSubscriber {

        @Subscribe
        public void handleShowHomeEvent(ShowHomeEvent event) {
            tournamentPane.setVisible(true);
        }
    }

    public class TournamentStartedMessageSubscriber {

        @Subscribe
        public void handleTournamentStartedMessage(TournamentStartedMessage tournamentStartedMessage){
            listTournamentModel.stream().forEach(tournament -> {
                if(tournament.getId().equals(tournamentStartedMessage.getId())){
                    tournament.setStarted(true);
                }
            });
        }
    }

    /**
     * La liste des joueurs inscrit au tournoi
     */
    private ObservableList<TournamentModel> listTournamentModel = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn idColumn = new TableColumn("Tournament id");
        idColumn.setCellValueFactory(new PropertyValueFactory<PlayerModel, String>("id"));

        TableColumn statusColumn = new TableColumn("Started");
        statusColumn.setCellValueFactory(new PropertyValueFactory<PlayerModel, Boolean>("started"));

        tounamentList.getColumns().addAll(idColumn,statusColumn);
        tounamentList.setItems(listTournamentModel);
        handleTransaction();

        tounamentList.setRowFactory( tv -> {
            TableRow<TournamentModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TournamentModel rowData = row.getItem();
                    tournamentPane.setVisible(false);
                    ShowTableEvent showTableEvent = new ShowTableEvent();
                    showTableEvent.setId(rowData.getId());
                    Controller.eventBus().post(showTableEvent);
                }
            });
            return row ;
        });

        Controller.tournamentApi().list().stream().forEach(tournament -> {
            TournamentModel model = new TournamentModel();
            model.setId(tournament.getId());
            model.setStarted(tournament.isStarted());
            listTournamentModel.addAll(model);
        });
    }

    /**
     * Ajout des subscriber dans le bus d'évènement
     */
    private void handleTransaction(){
        Controller.eventBus().register(new TournamentCreatedMessageSubscriber());
        Controller.eventBus().register(new ShowHomeEventSubscriber());
        Controller.eventBus().register(new TournamentStartedMessageSubscriber());
    }


    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String tournamentId = Controller.tournamentApi().createNewTournament();
        Controller.eventBus().post(new TournamentCreatedEvent(tournamentId));
    }

}
