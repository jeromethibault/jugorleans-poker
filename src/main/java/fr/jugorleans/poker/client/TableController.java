package fr.jugorleans.poker.client;


import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Created by francoispenaud on 27/04/15.
 */
public class TableController {

    @FXML
    private TableView leaders;

    public void showTable() {
        leaders.setVisible(true);
    }
}
