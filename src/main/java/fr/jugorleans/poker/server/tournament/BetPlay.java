package fr.jugorleans.poker.server.tournament;

import lombok.Builder;
import lombok.Getter;

/**
 * Classe représentant la mise investie par un joueur sur le round courant, ainsi que le total sur le play
 */
@Getter
@Builder
public class BetPlay {

    private int currentRound = 0;

    private int play = 0;

    public void update(int betValue){
        currentRound = currentRound + betValue;
        play = play + betValue;
    }

    public void newRound(){
        currentRound = 0;
    }
}
