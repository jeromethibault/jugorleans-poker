package fr.jugorleans.poker.server.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.jugorleans.poker.server.core.play.Blind;
import fr.jugorleans.poker.server.core.play.Player;
import fr.jugorleans.poker.server.core.play.Structure;
import fr.jugorleans.poker.server.tournament.Tournament;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * Service gérant les tournois
 * TODO ajouter persistence vers un spring data repository (H2 pour démarrer)
 */
@Component
public class TournamentService {

    private Map<String, Tournament> tempTournamentDAO = Maps.newHashMap(); // TODO remplacer par appel DAO

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

        tempTournamentDAO.put(tournament.getId(), tournament); // TODO remplacer par appel DAO
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
        Tournament tournament = tempTournamentDAO.get(id);
        Preconditions.checkState(Objects.nonNull(tournament), "Tournoi inconnu : " + id);
        Preconditions.checkState(!tournament.isStarted(), "Tournoi démarré : " + id);

        tournament.addPlayers(player);
        if (tournament.nbPlayersIn() == tournament.getNbMaxPlayers()) {
            tournament.start();
        }
        return tournament;
    }


}
