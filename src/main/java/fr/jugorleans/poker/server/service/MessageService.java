package fr.jugorleans.poker.server.service;

import fr.jugorleans.poker.server.core.Message;
import lombok.Builder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
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
        template.convertAndSend(destination, player);
    }
}
