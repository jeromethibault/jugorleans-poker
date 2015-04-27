package fr.jugorleans.poker.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TournamentController {

    @FXML
    private TableController tableController;


    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        System.out.println(actionEvent.getSource());
        ((Button) actionEvent.getSource()).setDisable(true);
        ((Button) actionEvent.getSource()).setVisible(false);
        tableController.showTable();
    }

}
