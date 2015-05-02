package fr.jugorleans.poker.api;

import com.google.common.collect.Maps;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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
}
