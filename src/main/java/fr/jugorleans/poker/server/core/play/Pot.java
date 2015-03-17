package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Pot contenant les mises des joueurs
 */
@Getter
@Setter
@ToString
public class Pot {

    /**
     * Montant du pot
     */
    private int amount = 0;

    /**
     * Montant du round courant surlequel il faut s'aligner pour accéder au round suivant
     */
    private int roundBet = 0;

    /**
     * Montant de la dernière relance
     */
    private int lastRaise = 0;

    /**
     * Joueurs pouvant se partager le pot
     */
    private List<Player> players = Lists.newArrayList();

    /**
     * Ajout d'une mise dans le pot principal
     *
     * @param betValue montant de la mise
     */
    public void addToPot(int betValue) {
        amount = amount + betValue;
    }

    /**
     * Prise en compte d'un nouveau round
     *
     * @param roundBet  montant roundbet (bb si début de partie, sinon 0)
     * @param lastRaise montant big blind (mise minimum)
     */
    public void newRound(int roundBet, int lastRaise) {
        this.roundBet = roundBet;
        this.lastRaise = lastRaise;
    }

    /**
     * Ajout d'un joueur en lisse pour le gain du pot
     *
     * @param player       joueur ajouté
     * @param betPlayValue mise globale investie par le joueur dans le play courant
     */
    public void addPlayer(Player player, int betPlayValue) {
        players.add(player);
        amount = amount + betPlayValue;
    }
}
