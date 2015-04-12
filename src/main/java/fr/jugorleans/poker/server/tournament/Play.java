package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.hand.Hand;
import fr.jugorleans.poker.server.core.play.*;
import fr.jugorleans.poker.server.game.DefaultStrongestHandResolver;
import fr.jugorleans.poker.server.tournament.action.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Partie (au sens une main) jouée entre plusieurs joueurs
 */
@Getter
@Setter
@Builder
@ToString
public class Play {

    /**
     * Id
     */
    private String id;

    /**
     * Pot principal
     */
    private Pot pot;

    /**
     * Liste des pots
     */
    private List<Pot> pots;

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
    private Map<Player, BetPlay> players;

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
    private Round currentRound;

    /**
     * Blinds courantes
     */
    private Blind currentBlind;

    /**
     * Tournoi
     */
    private Tournament tournament;

    /**
     * Timer de gestion automatiquement de fold si délai max atteint pour l'action d'un joueur
     */
    private Timer timerPlayer;

    /**
     * Ensemble des PlayerAction
     */
    private static final Map<Action, PlayerAction> ACTIONS = Maps.newHashMap();

    /**
     * Resolver permettant de résoudre la meilleure main lors du showdown
     */
    private DefaultStrongestHandResolver defaultStrongestHandResolver;

    /**
     * Initialisation de la liste des actions
     */
    static {
        ACTIONS.put(Action.CHECK, new CheckAction());
        ACTIONS.put(Action.CALL, new CallAction());
        ACTIONS.put(Action.BET, new BetAction());
        ACTIONS.put(Action.FOLD, new FoldAction());
        ACTIONS.put(Action.ALL_IN, new AllInAction());
    }


    /**
     * Démarrage de la main
     *
     * @param table table courante
     */
    public void start(Table table) {

        tournament = table.getTournament();

        id = table.getId() + "_" + StringUtils.leftPad(String.valueOf(tournament.getLastPlays().size()), 5, '0');

        // Passage de la main courante - FIXME Attention pas threadsafe pour multitables (mais on reste single table)
        ACTIONS.entrySet().forEach(impl -> impl.getValue().setPlay(this));

        // Positionnement du dealer (à conserver car important pour chaque début de round)
        seatCurrentDealer = table.getSeatPlayDealer();

        // Initialisation du pot, jeu de cartes, et du currentRound PREFLOP
        pot = new Pot();
        board = new Board();
        deck = new Deck();
        deck.shuffleUp();
        currentRound = Round.PREFLOP;

        // Distribution des cartes
        players = Maps.newHashMap();
        tournament.getPlayers().stream().filter(p -> !p.isOut()).forEach(p -> {
            p.setCurrentHand(Hand.newBuilder().firstCard(deck.deal()).secondCard(deck.deal()).build());
            p.setLastAction(Action.NONE);
            players.put(p, BetPlay.builder().build());
        });

        // Positionnement du joueur courant au niveau du dealer, puis passage au joueur UTGs
        seatCurrentPlayer = seatCurrentDealer;
        // Récupération blinds du round courant
        currentBlind = tournament.getStructure().getRounds().get(tournament.getCurrentBlindRound());
        Preconditions.checkState(currentBlind != null && currentBlind.isValid(), "Problème de structures - blinds incorrectes");
        Player sbPlayer = nextPlayer(); // small blind (ou BB en HU)
        Player bbPlayer = nextPlayer(); // big blind (ou SB en HU)
        nextPlayer(); // UTG

        // Gestion de la collecte des blinds
        collectBlinds(sbPlayer, bbPlayer);

        newTimerActionNextPlayer(1); // TODO externaliser le délai
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
     * Action d'un joueur
     *
     * @param player   joueur concerné
     * @param action   action du joueur
     * @param betValue montant de la mise
     * @return la main courante
     */
    public Player action(Player player, Action action, int betValue) {

        Preconditions.checkState(!Round.SHOWDOWN.equals(currentRound), "Play terminé");

        checkGoodPlayer(player);

        // Traitement de l'action du joueur
        try {
            ACTIONS.get(action).action(player, betValue);
        } catch (MustCallException e) {
            try {
                ACTIONS.get(Action.CALL).action(player, 0);
            } catch (MustCallException e1) {
                throw new IllegalStateException("Ne doit pas arriver");
            }
        }

        // Check si fin d'un round
        checkNewRound();

        // Passage au joueur suivant
        Player playerNext = nextPlayer();

        if (!Round.SHOWDOWN.equals(currentRound)) {
            // Positionnement du timer pour temps max joueur suivant
            newTimerActionNextPlayer(1); // TODO externaliser le délai
        }

        return playerNext;
    }

    /**
     * Mise à jour du montant investi par le joueur sur le round
     *
     * @param player joueur concerné
     * @param value  montant à cumuler
     */
    public void updatePlayerPlayAmount(Player player, int value) {
        players.get(player).update(value);
    }

    /**
     * Séparation des mises engagées par chacun des joueurs en différents pots (side pots éventuels dans le cas de allin)
     *
     * @return la liste des pots
     */
    public List<Pot> splitPot() {
        pots = pot.splitPot(players);
        return pots;
    }

    /**
     * Collecte des blinds
     *
     * @param sbPlayer joueur devant payer la SB
     * @param bbPlayer joueur devant payer la BB
     */
    private void collectBlinds(Player sbPlayer, Player bbPlayer) {
        // Collecte SB
        int sb = currentBlind.getSmallBlind();
        sbPlayer.paySmallBlind(sb);
        updatePlayerPlayAmount(sbPlayer, sb);

        // Collecte BB
        int bb = currentBlind.getBigBlind();
        bbPlayer.payBigBlind(bb);
        updatePlayerPlayAmount(bbPlayer, bb);

        // Collecte des antes s'il y en a sur le round
        int ante = currentBlind.getAnte();
        if (ante > 0) {
            players.keySet().forEach(p -> {
                p.payAnte(ante);
                updatePlayerPlayAmount(p, ante);
            });
        }

        // MAJ du pot
        int blindsSum = sb + bb + players.size() * ante;
        pot.addToPot(blindsSum);
        // Roundbet et lastraise positionnés à hauteur de la BB (car c'est la mise minimum autorisée par la première mise)
        pot.newRound(bb, bb);

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

        // Calcul moyenne du montant investi par les joueurs non foldés et non all in
        Double averageBetActivePlayers = players.entrySet().stream()
                .filter(p -> p.getKey().canPlay())
                .collect(Collectors.averagingInt(p -> p.getValue().getCurrentRound())).doubleValue();

        // Tous les joueurs non foldés et non allin ont-ils investi la moyenne précédemment calculée ?
        // == ont-ils tous fait la même mise ?
        boolean allActivePlayersHaveSameBet = players.entrySet().stream()
                .filter(p -> p.getKey().canPlay())
                .allMatch(p -> p.getValue().getCurrentRound() == averageBetActivePlayers.intValue());

        boolean inactivePlayers = countNbPlayersActive() == 0;

        // Nouveau round si les deux conditions sont remplies : tout le monde a joué et (tous ont fait la meme mise ou tous sont inactifs (non all in / non foldés)
        boolean newRound = everybodyPlays && (allActivePlayersHaveSameBet || inactivePlayers);
        if (newRound) {
            startNewRound();
        }

        return newRound;
    }

    /**
     * Démarrage d'un nouveau round
     */
    private void startNewRound() {
        // Cas avec un seul joueur restant => showdown individuel
        int nbPlayersActive = countNbPlayersActive();

        // S'il reste moins d'un joueur actif (pas foldé, pas all in) => showdown
        if (nbPlayersActive <= 1) {
            showdown();
        } else {
            // Passage au currentRound suivant
            currentRound = currentRound.next();
            if (Round.SHOWDOWN.equals(currentRound)) {
                showdown();
            } else {
                // Ajout d'éventuelles cartes sur le board
                deck.deal(); // Carte brûlée
                board.addCards(deck.deal(currentRound.nbCardsToAddOnBoard()));

                // Remise à 0 des mises du Round
                players.entrySet().stream().forEach(p -> p.getValue().newRound());

                // Remise à NONE des dernières actions des joueurs encore en jeu
                players.keySet()
                        .stream()
                        .filter(p -> p.canPlay())
                        .forEach(p -> p.setLastAction(Action.NONE));

                // Positionnement initial sur le dealer
                seatCurrentPlayer = seatCurrentDealer;

                // Prise en compte au niveau du pot
                pot.newRound(0, currentBlind.getBigBlind());
            }
        }
    }

    /**
     * Nombre de joueurs encore dans la main
     *
     * @return le nombre de joueurs non foldés
     */
    protected int countNbPlayersNotFolded() {
        return (int) players.keySet().stream().filter(p -> !p.isFolded()).count();
    }

    /**
     * Nombre de joueurs actifs (non folded et non all in)
     *
     * @return le nombre de joueurs correspondants
     */
    private int countNbPlayersActive() {
        return (int) players.keySet().stream().filter(p -> p.canPlay()).count();
    }

    /**
     * Showdown
     */
    private void showdown() {
        // Stop timer autocheck/fold car fin de la main
        timerPlayer.cancel();

//        if (countNbPlayersNotFolded() == 1) {
//            // Cas d'une fin de partie sans réel showdown (1 seul joueur restant)
//        } else {
        // Réel showdown
        Showdown showdown = new Showdown(this);
        showdown.setDefaultStrongestHandResolver(defaultStrongestHandResolver);
        showdown.show();
//        }

        // Vérification que chaque joueur n'est pas éliminé
        players.entrySet().forEach(p -> {
            p.getKey().checkIsOut();
            p.getKey().setAllIn(false);
        });

        currentRound = Round.SHOWDOWN;

        // Fin de la main
        tournament.checkEnd();
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

    /**
     * Recherche du prochain joueur
     *
     * @return le prochain joueur
     */
    private Optional<Player> findNextPlayer() {
        int nextSeatPlayer = 1 + seatCurrentPlayer % tournament.getPlayers().size(); // FIXME KO en multitables - joueurs à affecter à la table
        return players.keySet().stream()
                .filter(p -> (p.getSeat().getNumber() == nextSeatPlayer
                        && !p.isFolded()))
                .findFirst();
    }

    /**
     * Vérification que le joueur réalisant l'action est le joueur attendu
     *
     * @param player joueur réalisant l'action
     */
    private void checkGoodPlayer(Player player) {
        if (!whoMustPlay().equals(player)) {
            throw new IllegalStateException("Pas au tour du joueur " + player);
        }
    }


    /**
     * Positionnement d'un timer pour gérer autocheck/fold si le prochain joueur est trop lent
     *
     * @param delaySec délai en secondes
     */
    private void newTimerActionNextPlayer(long delaySec) {
        if (timerPlayer != null) {
            timerPlayer.cancel();
        }
        TimerTask timerTask = TimerTaskAction.builder().play(this).build();
        timerPlayer = new Timer("PlayAction" + id, true);
        timerPlayer.schedule(timerTask, delaySec * 1000);
    }

}
