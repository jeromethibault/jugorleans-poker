package fr.jugorleans.poker.server.util.test;

import fr.jugorleans.poker.server.util.Identification;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.util.Identification}
 */
public class IdentificationTest {

    @Test
    public void getIdTable(){
        Assert.assertEquals("IdTable incorrect", "xxx_yyy", Identification.getIdTableFromIdPlay("xxx_yyy_zzz"));
    }
}
