package fr.jugorleans.poker.server.repository;

import fr.jugorleans.poker.server.tournament.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by francoispenaud on 05/04/15.
 */
@Repository
public interface TournamentRepository extends CrudRepository<Tournament, String> {
}
