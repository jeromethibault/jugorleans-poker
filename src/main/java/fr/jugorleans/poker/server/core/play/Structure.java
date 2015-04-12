package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Structure d'un tournoi
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "T_STRUCTURE")
public class Structure {

    /**
     * Map des rounds (clé étant numéro du currentRound de 1 à n)
     */
    private Map<Integer, Blind> rounds;

    /**
     * Durée d'un round en minutes
     */
    private int duration;

    /**
     * Ajouter des blinds à la structure
     *
     * @param blinds blinds à ajouter
     */
    public void initializeRounds(Blind... blinds) {
        rounds = Maps.newHashMap();
        AtomicInteger index = new AtomicInteger(0);
        Arrays.stream(blinds).sequential().forEach(b -> rounds.put(index.incrementAndGet(), b));
    }

    /**
     * Vérification de la validité de la structure
     *
     * @return vrai si au moins un currentRound est présent et que tous les rounds sont valides, faux dans le cas contraire
     */
    public boolean isValid() {
        return !rounds.isEmpty() && rounds.entrySet().stream().allMatch(entry -> entry.getValue().isValid());
    }
}
