package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.*;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Showdown de fin de play
 */
@Setter
public class Showdown {

    /**
     * Main courante
     */
    private Play play;

    /**
     * Resolver permettant de résoudre la meilleure main lors du showdown
     */
    private DefaultStrongestHandResolver defaultStrongestHandResolver;

    /**
     * Constructeur
     *
     * @param play main courante
     */
    public Showdown(Play play) {
        this.play = play;
    }

    /**
     * Showdown
     */
    public void show() {
        Board board = play.getBoard();
        Deck deck = play.getDeck();
        Round currentRound = play.getCurrentRound();

        // Détermination des différents pots
        List<Pot> pots = play.splitPot();

        // Cas d'un seul joueur en jeu (les autres ont foldé)
        if (play.countNbPlayersNotFolded() == 1) {
            Preconditions.checkState(pots.size() == 1, "Nombre de pots incohérents");
            Pot pot = pots.get(0);
            // Le vainqueur est l'unique joueur restant
            pot.setWinners(pot.getPlayers());
            sharePotBetweenPlayers(pot);
        } else {
            // Cas d'un vrai showdown avec plusieurs joueurs

            // Cas board non complet --> on complète les cartes
            while (!board.isFull()) {
                // Passage au currentRound suivant
                deck.deal(); // Carte brûlée
                currentRound = currentRound.next();
                board.addCards(deck.deal(currentRound.nbCardsToAddOnBoard()));
            }

            // Traitement de chaque pot
            pots.forEach(sp -> {
                // Récupération des mains des joueurs en jeu
                List<Hand> hands = sp.getPlayers().stream().map(p -> p.getCurrentHand()).collect(Collectors.toList());

                // Récupération des mains gagnantes
                List<Hand> winningHands = defaultStrongestHandResolver.getWinningHand(board, hands);

                // Récupération des joueurs gagnants
                List<Player> winners = sp.getPlayers().stream().filter(p -> winningHands.contains(p.getCurrentHand())).collect(Collectors.toList());
                sp.setWinners(winners);
                sharePotBetweenPlayers(sp);
            });

        }
    }


    /**
     * Distribution des gains d'un pot entre les vainqueurs
     *
     * @param pot pot à partager
     */
    private void sharePotBetweenPlayers(Pot pot) {
        // Distribution des gains
        int potSplit = pot.getAmount() / pot.getWinners().size();
        pot.getWinners().forEach(p -> p.win(potSplit));
    }

}
