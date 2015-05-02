package fr.jugorleans.poker.client.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.io.IOException;

/**
 * Classe se chargeant de déterminer le type d'un message JSON
 *
 * @author THIBAULT Jérôme
 */
public class MessageTypeHandler {

    /**
     * Jackson Object Mapper
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param message le message JSON
     * @return le type du message
     */
    public static String typeOf(String message){
        try {
            MessageType messageType = objectMapper.readValue(message, MessageType.class);
            return messageType.getType();
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return null;
    }
}
