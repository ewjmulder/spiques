package com.programyourhome.spiques.master;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.programyourhome.spiques.master.server.SpiquesMasterServer;

@SpringBootApplication
@PropertySource("file:${spiques.master.properties.location}")
public class SpiquesMasterSpringBootApplication {

    public static void startApplication() {
    	SpiquesMasterServer.startServer(SpiquesMasterSpringBootApplication.class);
    }

}
