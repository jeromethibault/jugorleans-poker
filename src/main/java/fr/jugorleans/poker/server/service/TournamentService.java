package fr.jugorleans.poker.server.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Blind;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Structure;
import fr.jugorleans.poker.server.tournament.Play;
import fr.jugorleans.poker.server.tournament.Tournament;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service gérant les tournois
 * TODO ajouter persistence vers un spring data repository (H2 pour démarrer)
 */
@Component
@Log
public class TournamentService {

    private Map<String, Tournament> tempTournamentDAO = Maps.newHashMap(); // TODO remplacer par appel DAO
    private Map<String, Play> tempPlayDAO = Maps.newHashMap(); // TODO remplacer par appel DAO

    /**
     * Service gérant l'envoie des notification
     */
    @Autowired
    private MessageService messageService;

    /**
     * Création d'un nouveau tournoi
     *
     * @return son id
     */
    public Tournament createNewTournament() {
        // TODO gérer une structure par défaut
        Structure structure = Structure.builder().duration(20).build();
        Blind blind = Blind.builder().smallBlind(10).bigBlind(20).build();
        structure.initializeRounds(blind, blind, blind, blind);

        Tournament tournament = Tournament.builder()
                .structure(structure)
                .initialStack(10000) // TODO gérer class constantes default_value ou examiner pourquoi builder initialise à null
                .nbMaxPlayers(4)
                .lastPlays(Lists.newArrayList())
                .build().init();

        tournament.setId(tournament.getId());
        tempTournamentDAO.put(tournament.getId(), tournament); // TODO remplacer par appel DAO
        log.info(tournament.getId().toString());
        messageService.tournamentCreatedEvent(tournament.getId());
        return tournament;
    }

    /**
     * @return la liste des tournois
     */
    public Collection<Tournament> findAll(){
        return tempTournamentDAO.values();
    }

    /**
     * Obtenir la liste des joueurs d'un tournoi
     *
     * @param tournamentId l'identifiant du tournoi
     * @return la liste des joueurs
     */
    public List<Player> findPlayers(String tournamentId){
        List<Player> list = tempTournamentDAO.get(tournamentId).getPlayers();
        return list!=null?list:Lists.newArrayList();
    }

    /**
     * Restituer un tournoi selon son id
     *
     * @param id id du tournoi
     * @return le tournoi
     */
    public Tournament getTournamentById(String id) {
        log.info(id);
        return tempTournamentDAO.get(id);
    }

    /**
     * Ajout d'un joueur avec démarrage automatique quand le nombre de joueurs attendu est atteint
     *
     * @param id     id du tournoi
     * @param player joueur
     * @return le tournoi
     */
    public synchronized Tournament addPlayer(String id, Player player) { // TODO gérer la synchro plus proprement
        Tournament tournament = tempTournamentDAO.get(id);
        Preconditions.checkState(Objects.nonNull(tournament), "Tournoi inconnu : " + id);
        Preconditions.checkState(!tournament.isStarted(), "Tournoi démarré : " + id);

        // Todo Assigner une place au joueur
        // Todo assigner un stack

        tournament.addPlayers(player);
        messageService.addPlayerEvent(player.getNickName(),id);
        if (tournament.nbPlayersIn() == tournament.getNbMaxPlayers()) {
            tournament.start();
            messageService.tournamentStartedEvent(id);
        }
        return tournament;
    }

    /**
     * Action d'un joueur
     *
     * @param idPlay id du Play
     * @param player joueur concerné
     * @param action action réalisée
     * @param bet    montant de l'éventuelle mise
     * @return le Play actualisé
     */
    public Play action(String idPlay, Player player, Action action, int bet) {
        Play play = tempPlayDAO.get(idPlay);
        play.action(player, action, bet);
        return play;
    }


}
