package fr.jugorleans.poker.server.endpoint;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.service.TournamentService;
import fr.jugorleans.poker.server.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposant les méthodes pour administrer les tournois
 */
@RestController
@RequestMapping("/game")
@Slf4j
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tournament getTournamentById(@PathVariable String id) {
        return tournamentService.getTournamentById(id);
    }

    /**
     * Inscription à un tournoi
     *
     * @param id identifiant du tournoi
     * @return le tournoi
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Tournament register(@PathVariable String id, @RequestBody String nickname) {
        return tournamentService.addPlayer(id, Player.builder().nickName(nickname).build());
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @SendTo("/websocket/test")
    public String test() {
        System.out.println("Connexion d'un joueur");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "message via web socket";
    }


}
