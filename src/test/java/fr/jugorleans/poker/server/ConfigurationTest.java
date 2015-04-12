package fr.jugorleans.poker.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration pour les tests
 */
@Configuration
@ComponentScan()
@EnableAutoConfiguration
//@Profile(Application.SPRING_PROFILE_DEVELOPMENT)
public class ConfigurationTest {
}
