package integration;

import fr.jugorleans.poker.server.conf.test.ConfigurationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test d'intégration
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
@Slf4j
public class AutoCheckFoldTimerTest extends AbstractInitTournament {


    @Test
    public void testAutoCheck() {

    }
}
