package fr.jugorleans.poker.server.service;

import fr.jugorleans.poker.server.message.AddPlayerMessage;
import fr.jugorleans.poker.server.message.TournamentCreatedMessage;
import fr.jugorleans.poker.server.message.TournamentStartedMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Service en charge de l'envoie des notification vers les joueurs
 *
 * @author THIBAULT Jérôme
 */
@Component
public class MessageService implements InitializingBean{

    @Autowired
    private SimpMessagingTemplate template;

    private String destination = "/websocket/test";

    @Override
    public void afterPropertiesSet() throws Exception {
        template.setMessageConverter(new MappingJackson2MessageConverter());
    }

    /**
     * Envoyer une notification de type "addPlayer"
     *
     * @param player pseudo du joueur inscrit au tournoi
     */
    public void addPlayerEvent(String player,String tournamentId){
        AddPlayerMessage addPlayerMessage = new AddPlayerMessage();
        addPlayerMessage.setNickname(player);
        addPlayerMessage.setIdTournament(tournamentId);
        template.convertAndSend(destination, addPlayerMessage);
    }

    /**
     * Envoyer une notification de type "tournamentCreated"
     *
     * @param id identifiant du tournoi
     */
    public void tournamentCreatedEvent(String id){
        TournamentCreatedMessage tournamentCreatedMessage = new TournamentCreatedMessage();
        tournamentCreatedMessage.setId(id);
        template.convertAndSend(destination,tournamentCreatedMessage);
    }

    /**
     * Envoyer une notification de type "tournamentStarted
     *
     * @param id identifiant du tournoi
     */
    public void tournamentStartedEvent(String id) {
        TournamentStartedMessage tournamentStartedMessage = new TournamentStartedMessage();
        tournamentStartedMessage.setId(id);
        template.convertAndSend(destination,tournamentStartedMessage);
    }
}
