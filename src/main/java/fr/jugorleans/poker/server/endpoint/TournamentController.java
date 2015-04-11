package fr.jugorleans.poker.server.endpoint;

import fr.jugorleans.poker.server.service.TournamentService;
import fr.jugorleans.poker.server.tournament.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposant les méthodes pour administrer les tournois
 */
@RestController
@RequestMapping("/tournament")
public class TournamentController {

    /**
     * Service gérant les tournois
     */
    @Autowired
    private TournamentService tournamentService;

    /**
     * @return le nouveau tournois
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tournament createNewTournament() {
        return tournamentService.createNewTournament();
    }

    /**
     * @param id identifiant du tournoi
     * @return le tournoi correspondant
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tournament getTournamentById(@PathVariable String id){
        return tournamentService.getTournamentById(id);
    }


}
