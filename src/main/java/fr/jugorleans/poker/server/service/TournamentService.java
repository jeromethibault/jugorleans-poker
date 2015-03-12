package fr.jugorleans.poker.server.service;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.core.play.Blind;
import fr.jugorleans.poker.server.core.play.Structure;
import fr.jugorleans.poker.server.tournament.Tournament;
import org.springframework.stereotype.Component;

/**
 * Service gérant les tournois
 * TODO ajouter persistence vers un spring data repository (H2 pour démarrer)
 */
@Component
public class TournamentService {

    /**
     * Création d'un nouveau tournoi
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
                .nbMaxPlayers(10)
                .lastPlays(Lists.newArrayList())
                .build();
        tournament.init();
        return tournament;
    }


}
