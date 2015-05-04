package fr.jugorleans.poker.api;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.api.ressource.Player;
import fr.jugorleans.poker.api.ressource.Tournament;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Api de gestion des tournois
 *
 * @author THIBAULT Jérôme
 */
public class TournamentApi {

    /**
     * Spring rest template
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * L'url de base
     */
    private String baseUrl = "http://localhost:8080/";

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Créer un nouveau tournoi
     */
    public String createNewTournament(){
        return restTemplate.postForEntity(this.baseUrl + "/game", null, String.class).getBody();
    }

    /**
     * Enregister un joueur à un tournoi identifié par son id
     *
     * @param tournamentId l'identifiant du tournoi
     * @param nickname le pseudo du joueur
     */
    public void register(String tournamentId, String nickname){
        Map pathVariable = Maps.newHashMapWithExpectedSize(1);
        pathVariable.put("tournamentId",tournamentId);
        HttpEntity<String> entity = new HttpEntity<String>(nickname, getHeaders());
        restTemplate.exchange(this.baseUrl + "/game/{tournamentId}", HttpMethod.PUT, entity,Void.class, pathVariable);
    }

    /**
     * @return la list des tournoi
     */
    public List<Tournament> list(){
        ResponseEntity<Tournament[]> responseEntity = restTemplate.getForEntity(this.baseUrl + "/game",Tournament[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    /**
     * @param tournamentId l'identifiant du tournoi
     * @return les joueurs présent dans le tournoi
     */
    public List<Player> findPlayers(String tournamentId){
        Map<String,String> args = Maps.newHashMapWithExpectedSize(1);
        args.put("id",tournamentId);
        ResponseEntity<Player[]> responseEntity = restTemplate.getForEntity(this.baseUrl + "/game/{id}/_players",Player[].class, args);
        return Arrays.asList(responseEntity.getBody());
    }
}
