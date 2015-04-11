package fr.jugorleans.poker.server;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Bootstrap du poker server
 */
@SpringBootApplication
@Log
public class PokerServer {

    public static void main(String[] args){
        ApplicationContext ctx =SpringApplication.run(PokerServer.class, args);
        for (String beanName : ctx.getBeanDefinitionNames()) {
            log.info(beanName);
        }
    }
}
