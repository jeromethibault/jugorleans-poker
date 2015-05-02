package fr.jugorleans.poker.client;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import fr.jugorleans.poker.api.TournamentApi;

/**
 * Controller parent
 */
public class Controller {

    /**
     * Api de gestion des tournois
     */
    private static TournamentApi tournamentApi = new TournamentApi();

    /**
     * Le bus d'évènement
     */
    private static EventBus eventBus = new EventBus(new SubscriberExceptionHandler(){
        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            exception.printStackTrace();
        }
    });

    /**
     * @return l'api tournoi
     */
    public static TournamentApi tournamentApi() {
        return tournamentApi;
    }

    /**
     * @return l'event bus
     */
    public static EventBus eventBus() {
        return eventBus;
    }
}
