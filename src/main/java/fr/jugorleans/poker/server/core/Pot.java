package fr.jugorleans.poker.server.core;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Représente le pot
 */
public class Pot {

    /**
     * le montant du pot
     */
    private Integer amount = 0;

    /**
     * La liste des side pots
     */
    private List<Integer> sidePot = Lists.newArrayList();


}
