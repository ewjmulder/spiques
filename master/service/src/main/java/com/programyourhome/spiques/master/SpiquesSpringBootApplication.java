package com.programyourhome.spiques.master;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.programyourhome.spiques.master.server.SpiquesServer;

@SpringBootApplication
@PropertySource("file:${spiques.properties.location}")
public class SpiquesSpringBootApplication {

    public static void startApplication() {
    	SpiquesServer.startServer(SpiquesSpringBootApplication.class);
    }

}
