package fr.jugorleans.poker.client.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import fr.jugorleans.poker.client.Controller;
import fr.jugorleans.poker.client.event.AddPlayerInTournamentEvent;
import fr.jugorleans.poker.server.message.AddPlayerMessage;

import java.io.IOException;

/**
 * Classe de chargeant de lançer les traitements apropriés en fonction du
 * type du message
 *
 * @author THIBAULT Jérôme
 */
public class MessageHandler {

    /**
     * Jackson Object Mapper
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Prise en charge du message JSON
     *
     * @param message le message JSON
     */
    public static void handleMessage(String message){
        try{
            String type = MessageTypeHandler.typeOf(message);
            if("addPlayer".equals(type)){
                AddPlayerMessage addPlayerMessage = objectMapper.readValue(message,AddPlayerMessage.class);
                // TODO event inutile poster directement le message
                Controller.eventBus().post(new AddPlayerInTournamentEvent(addPlayerMessage.getNickname()));
                return;
            }
            if("tournamentCreated".equals(type)){
                Controller.eventBus().post(objectMapper.readValue(message, TournamentCreatedMessage.class));
                return;
            }
        }catch (IOException e){
            Throwables.propagate(e);
        }

    }
}
