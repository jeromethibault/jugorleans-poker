package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.tournament.Player;
import fr.jugorleans.poker.server.core.tournament.Tournament;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

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
    private Map<Player, Integer> players = Maps.newHashMap();

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
        seatCurrentDealer = tournament.getSeatPlayDealer();

        pot = new Pot();
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

        pot.addToPot(betValue);
        player.bet(betValue);
        checkNewRound();
        nextPlayer();

        return this;
    }


    public Play fold(Player player) {
        checkGoodPlayer(player);
        player.fold();
        players.remove(player);
        checkNewRound();
        nextPlayer();

        return this;
    }

    private boolean checkNewRound() {
        boolean everybodyPlays = players.keySet().stream()
                .noneMatch(p -> p.getLastAction() != Action.NONE);

        Double averageBetActivePlayers = players.entrySet().stream()
                .filter(p -> p.getKey().getLastAction() != Action.FOLD)
                .mapToInt(p -> p.getValue()).average().orElse(0.0);

        boolean allActivePlayersHaveSameBet = players.entrySet().stream()
                .filter(p -> p.getKey().getLastAction() != Action.FOLD)
                .allMatch(p -> p.getValue() == averageBetActivePlayers.intValue());

        boolean newRound = everybodyPlays && allActivePlayersHaveSameBet;
        if (newRound) {
            round = round.next();
        }

        return newRound;
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
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer && !p.isOut()))
                .findFirst();
    }

    private void checkGoodPlayer(Player player) {
        if (!whoMustPlay().equals(player)) {
            throw new RuntimeException("Pas au tour du joueur " + player);
        }
    }


}
