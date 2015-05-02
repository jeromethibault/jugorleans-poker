package fr.jugorleans.poker.client;

import com.google.common.eventbus.EventBus;
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
    private static EventBus eventBus = new EventBus();

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
