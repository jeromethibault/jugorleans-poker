package fr.jugorleans.poker.server.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.play.Action;
import fr.jugorleans.poker.server.core.play.Blind;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Structure;
import fr.jugorleans.poker.server.repository.TournamentRepository;
import fr.jugorleans.poker.server.tournament.Play;
import fr.jugorleans.poker.server.tournament.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * Service gérant les tournois
 * TODO ajouter persistence vers un spring data repository (H2 pour démarrer)
 */
@Component
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    private Map<String, Play> tempPlayDAO = Maps.newHashMap(); // TODO remplacer par appel DAO

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

        Tournament tournament = new Tournament();
        tournament.setStructure(structure);
        tournament.setInitialStack(10000);
        tournament.setNbMaxPlayers(4);
        tournament.init();
        tournamentRepository.save(tournament);

        return tournament;
    }

    /**
     * Ajout d'un joueur avec démarrage automatique quand le nombre de joueurs attendu est atteint
     *
     * @param id     id du tournoi
     * @param player joueur
     * @return le tournoi
     */
    public synchronized Tournament addPlayer(String id, Player player) { // TODO gérer la synchro plus proprement
        Tournament tournament = tournamentRepository.findOne(id);
        Preconditions.checkState(Objects.nonNull(tournament), "Tournoi inconnu : " + id);
        Preconditions.checkState(!tournament.isStarted(), "Tournoi démarré : " + id);

        tournament.addPlayers(player);
        if (tournament.nbPlayersIn() == tournament.getNbMaxPlayers()) {
            tournament.start();
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
