package fr.jugorleans.poker.client;


import com.google.common.eventbus.Subscribe;
import fr.jugorleans.poker.client.event.TournamentCreatedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Created by francoispenaud on 27/04/15.
 */
public class TableController {

    public class TournamentCreatedSubscriber{

        @Subscribe
        public void handleTournamentCreatedEvent(TournamentCreatedEvent event){
            System.out.println("Reception évènement TournamentCreated");
            tournamentId = event.getTournamentId();
        }
    }

    private void handleTransaction(){
        Controller.eventBus().register(new TournamentCreatedSubscriber());
    }

    public TableController(){
        handleTransaction();
    }

    @FXML
    private TableView leaders;

    private String tournamentId;

    public void showTable() {
        leaders.setVisible(true);
    }

    public void addPlayer(){
        Controller.tournamentApi().register(this.tournamentId,"toto");
    }
}
