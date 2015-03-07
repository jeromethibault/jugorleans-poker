package fr.jugorleans.poker.server.tournament;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Partie (au sens une main) jouée entre plusieurs joueurs
 */
@Getter
@Builder
@ToString
public class Play {


    /**
     * Pots (principal et éventuels secondaires)
     */
    private Pot pot;

    /**
     * Paquet de cartes
     */
    private Deck deck;

    /**
     * Board
     */
    private Board board;

    /**
     * Joueurs et montants associées investis pour le joueur durant cette main
     */
    private Map<Player, Integer> players;

    /**
     * Joueur qui est en train de jouer (identifié par son numéro siège)
     */
    private int seatCurrentPlayer;

    /**
     * Joueur actuellement dealer (identifié par son numéro de siège)
     */
    private int seatCurrentDealer;

    /**
     * Round courant
     */
    private Round round;

    /**
     * Main démarrée
     */
    private boolean started = false;


    public void start(Tournament tournament) {
        // Positionnement du dealer (à conserver car important pour chaque début de round)
        seatCurrentDealer = tournament.getSeatPlayDealer();

        // Initialisation du pot, jeu de cartes, et du round PREFLOP
        pot = new Pot();
        board = new Board();
        deck = new Deck();
        deck.shuffleUp();
        round = Round.PREFLOP;

        // Distribution des cartes
        players = Maps.newHashMap();
        tournament.getPlayers().stream().filter(p -> !p.isOut()).forEach(p -> {
            p.setCurrentHand(Hand.newBuilder().firstCard(deck.deal()).secondCard(deck.deal()).build());
            p.setLastAction(Action.NONE);
            players.put(p, 0);
        });

        // TODO gerer les blinds (attention HU)

        // Positionnement du joueur courant au niveau du dealer, puis passage au joueur UTGs
        seatCurrentPlayer = seatCurrentDealer;
        nextPlayer(); // small blind (ou BB en HU)
        nextPlayer(); // big blind (ou SB en HU)
        nextPlayer(); // UTG
    }

    /**
     * Identification du joueur dont on attend l'action
     *
     * @return le joueur
     */
    public Player whoMustPlay() {
        return players.keySet().stream().filter(p -> p.getSeat().getNumber() == seatCurrentPlayer).findFirst().get();
    }

    /**
     * Mise d'un joueur
     *
     * @param player   joueur concerné
     * @param betValue montant de la mise
     * @return la main courante
     */
    public Play bet(Player player, int betValue) {
        checkGoodPlayer(player);

        // Bet = MAJ du pot, diminution du stack du joueur et MAJ montant engagé sur le round
        pot.addToPot(betValue);
        player.bet(betValue);
        players.merge(player, betValue, (v1, v2) -> v1 + v2);

        // Check si fin d'un round
        checkNewRound();

        // Passage au joueur suivant
        nextPlayer();

        return this;
    }


    /**
     * Fold d'un joueur
     *
     * @param player joueur qui fold
     * @return la main courante
     */
    public Play fold(Player player) {
        checkGoodPlayer(player);

        // Fold du joueur
        player.fold();

        // Check si fin d'un round
        checkNewRound();

        // Passage au joueur suivant
        nextPlayer();

        return this;

    }

    /**
     * Vérification d'un éventuel changement de round
     *
     * @return vrai si la dernière action a engendré un changement de round
     */
    private boolean checkNewRound() {
        // Tous les joueurs ont-ils joué ?
        boolean everybodyPlays = players.keySet().stream()
                .noneMatch(p -> Action.NONE.equals(p.getLastAction()));

        // Calcul moyenne du montant investi par les joueurs non foldés
        Double averageBetActivePlayers = players.entrySet().stream()
                .filter(p -> !Action.FOLD.equals(p.getKey().getLastAction()))
                .collect(Collectors.averagingInt(p -> p.getValue())).doubleValue();

        // Tous les joueurs non foldés ont-ils investi la moyenne précédemment calculée ?
        // == ont-ils tous fait la même mise ?
        boolean allActivePlayersHaveSameBet = players.entrySet().stream()
                .filter(p -> p.getKey().getLastAction() != Action.FOLD)
                .allMatch(p -> p.getValue() == averageBetActivePlayers.intValue());

        // Nouveau round si les deux conditions sont remplies
        boolean newRound = everybodyPlays && allActivePlayersHaveSameBet;
        if (newRound) {
            startNewRound();
        }

        return newRound;
    }

    /**
     * Démarrage d'un nouveau round
     */
    private void startNewRound() {
        // Passage au round suivant
        round = round.next();

        // Ajout d'éventuelles cartes sur le board
        board.addCards(deck.deal(round.nbCardsToAddOnBoard()));
    }

    /**
     * Passage au prochain joueur
     *
     * @return le prochain joueur
     */
    private Player nextPlayer() {
        Optional<Player> next = findNextPlayer();
        while (!next.isPresent()) {
            seatCurrentPlayer++;
            next = findNextPlayer();
        }

        seatCurrentPlayer = next.get().getSeat().getNumber();
        return next.get();
    }

    private Optional<Player> findNextPlayer() {
        int nextSeatPlayer = 1 + seatCurrentPlayer % players.size();
        return players.keySet().stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer
                        && !Action.FOLD.equals(p.getLastAction())))
                .findFirst();
    }

    private void checkGoodPlayer(Player player) {
        if (!whoMustPlay().equals(player)) {
            throw new RuntimeException("Pas au tour du joueur " + player);
        }
    }


}
