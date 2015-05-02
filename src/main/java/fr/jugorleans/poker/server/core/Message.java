package fr.jugorleans.poker.server.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author THIBAULT Jérôme
 *
 * TODO version à enrichir
 */
@Builder
@Getter
@Setter
public class Message {

    private String action;

    private String body;
}
