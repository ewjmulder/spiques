package com.programyourhome.spiques.tile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.programyourhome.spiques.tile.server.SpiquesTileServer;

@SpringBootApplication
@PropertySource("file:${spiques.tile.properties.location}")
public class SpiquesTileSpringBootApplication {

    public static void startApplication() {
    	SpiquesTileServer.startServer(SpiquesTileSpringBootApplication.class);
    }

}
