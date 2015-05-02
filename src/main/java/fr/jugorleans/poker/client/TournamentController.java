package fr.jugorleans.poker.client;

import fr.jugorleans.poker.client.event.TournamentCreatedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TournamentController {

    @FXML
    private TableController tableController;

    private MyWebSocketHandler socketHandler;

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).setDisable(true);
        ((Button) actionEvent.getSource()).setVisible(false);
        String tournamentId = Controller.tournamentApi().createNewTournament();
        Controller.eventBus().post(new TournamentCreatedEvent(tournamentId));
        tableController.showTable();
    }

    public void setSocketHandler(MyWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }
}
