package fr.jugorleans.poker.server.core.play;

import com.google.common.collect.Lists;
import fr.jugorleans.poker.server.tournament.BetPlay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pot contenant les mises des joueurs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "T_POT")
public class Pot implements Serializable {

    @Id
    private Long id;

    /**
     * Montant du pot
     */
    private int amount = 0;

    /**
     * Montant du round courant surlequel il faut s'aligner pour accéder au round suivant
     */
    private int roundBet = 0;

    /**
     * Montant de la dernière relance
     */
    private int lastRaise = 0;

    /**
     * Joueurs pouvant se partager le pot
     */
    @Transient
    private List<Player> players = Lists.newArrayList();

    /**
     * Joueurs vainqueurs du pot
     */
    @Transient
    private List<Player> winners = Lists.newArrayList();

    public Pot() {
    }

    /**
     * Ajout d'une mise dans le pot principal
     *
     * @param betValue montant de la mise
     */
    public void addToPot(int betValue) {
        amount = amount + betValue;
    }

    /**
     * Prise en compte d'un nouveau round
     *
     * @param roundBet  montant roundbet (bb si début de partie, sinon 0)
     * @param lastRaise montant big blind (mise minimum)
     */
    public void newRound(int roundBet, int lastRaise) {
        this.roundBet = roundBet;
        this.lastRaise = lastRaise;
    }

    /**
     * Ajout d'un joueur en lisse pour le gain du pot
     *
     * @param player       joueur ajouté
     * @param betPlayValue mise globale investie par le joueur dans le play courant
     */
    public void addPlayer(Player player, int betPlayValue) {
        players.add(player);
        amount = amount + betPlayValue;
    }

    /**
     * Séparation des mises engagées par chacun des joueurs en différents pots (side pots éventuels dans le cas de allin)
     *
     * @param players joueurs
     * @return la liste des pots
     */
    public List<Pot> splitPot(Map<Player, BetPlay> players) {

        List<Pot> splittedPots = Lists.newArrayList();
        AtomicInteger lastSidePotLevel = new AtomicInteger(0);

        // Parcours des différentes valeurs de mises investies (paliers) par les joueurs (=> permet de définir le nombre de pots différents)
        players.entrySet().stream()
                .filter(p -> !p.getKey().isFolded())
                .mapToInt(p -> p.getValue().getPlay())
                .distinct()
                .sorted()
                .sequential()
                        // Pour chacun des paliers différents
                .forEach(i -> {
                    // Création d'un side pot
                    Pot sidePot = new Pot();
                    splittedPots.add(sidePot);
                    // On alimente le pot avec les joueurs concernés (ceux qui ont investi autant ou plus que le palier)
                    players.entrySet()
                            .stream()
                            .filter(p -> p.getValue().getPlay() >= i)
                            .forEach(p -> {
                                sidePot.addPlayer(p.getKey(), i - lastSidePotLevel.get());
                            });
                    lastSidePotLevel.set(i);
                });

        // Prise en compte des blinds si elles concernent des joueurs foldés
        int blinds = amount - splittedPots.stream().mapToInt(p -> p.getAmount()).sum();
        if (blinds > 0) {
            splittedPots.stream().sorted((p1, p2) -> p2.getAmount() - p1.getAmount()).findFirst().get().addToPot(blinds);
        }

        return splittedPots;
    }
}
