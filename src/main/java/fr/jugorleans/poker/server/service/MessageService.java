package fr.jugorleans.poker.server.service;

import fr.jugorleans.poker.server.message.AddPlayerMessage;
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

    public void addPlayerEvent(String player){
        AddPlayerMessage addPlayerMessage = new AddPlayerMessage();
        addPlayerMessage.setNickname(player);
        template.convertAndSend(destination, addPlayerMessage);
    }
}
