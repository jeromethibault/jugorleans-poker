package fr.jugorleans.poker.server.core.play;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

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
    private List<Player> players;

    /**
     * Liste des éventuels side pots
     */
    private Optional<List<Pot>> sidePots = Optional.empty();

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

}
