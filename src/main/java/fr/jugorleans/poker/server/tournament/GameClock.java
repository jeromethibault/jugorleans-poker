package fr.jugorleans.poker.server.tournament;

import com.google.common.base.Preconditions;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Horloge de la partie
 */
@Setter
public class GameClock {

    /**
     * Date de début
     */
    private ChronoLocalDateTime startTime;

    /**
     * Date de fin
     */
    private LocalDateTime endTime;

    /**
     * Constructeur
     *
     * @param startTime date de début
     */
    public void start(LocalDateTime startTime) {
        this.startTime = ChronoLocalDateTime.from(startTime);
    }

    /**
     * Temps passé en secondes depuis le démarrage
     *
     * @return le nb de secondes
     */
    public long getElapsedTime() {
        Preconditions.checkState(startTime != null, "Clock non démarrée");
        return Instant.ofEpochSecond(startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS)).getEpochSecond();
    }
}