package fr.jugorleans.poker.server.tournament.action;

import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.tournament.Play;

/**
 * Created by francoispenaud on 07/03/15.
 */
public interface PlayerAction {

    /**
     * Affectation du play courant
     *
     * @param play main courante
     */
    void setPlay(Play play);

    /**
     * Action d'un joueur
     *
     * @param player joueur concerné
     * @param bet    montant investi dans cette action
     * @return le Play courant
     */
    Play action(Player player, int bet);
}
