package com.jac.fsd.musicplanet;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicplanetApplication {

    public static void main(String[] args) {
        /*
         * Load the .env file and set the environment variables
         */
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(MusicplanetApplication.class, args);
    }

}
