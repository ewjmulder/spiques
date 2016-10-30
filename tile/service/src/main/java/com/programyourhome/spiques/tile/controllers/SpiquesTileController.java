package com.programyourhome.spiques.tile.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programyourhome.spiques.tile.javafx.DisplayAnswer;
import com.programyourhome.spiques.tile.server.SpiquesTileServer;

@RestController
@RequestMapping("spiques")
public class SpiquesTileController {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${tile.code}")
	private int tileCode;

	@Value("${master.ip}")
	private String masterIp;
	
	private Optional<Process> currectPiWallListener;
	private Optional<Process> displayAnswerApplication;
	
	public SpiquesTileController() {
		this.currectPiWallListener = Optional.empty();
		this.displayAnswerApplication = Optional.empty();
	}
	
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "listener/start", method = RequestMethod.POST)
    public void startPiWallListener() throws IOException, InterruptedException {
    	this.stopDisplayAnswer();
    	this.stopPiWallListener();
    	//TODO: stream process output to own output, for debugging purposes
    	this.currectPiWallListener = Optional.of(Runtime.getRuntime().exec("pwomxplayer --tile-code=" + tileCode + " udp://239.0.1.23:1234?buffer_size=1200000B"));
    }

    @RequestMapping(value = "listener/stop", method = RequestMethod.POST)
	public void stopPiWallListener() {
    	try {
    		this.currectPiWallListener.ifPresent(Process::destroyForcibly);
    	} catch (Exception e) {
    		//TODO: use logging system
    		System.out.println("WARN: exception during closing of pi wall listener");
    	} finally {
    		this.currectPiWallListener = Optional.empty();			
    	}
	}

    // TODO: use shared model
    @RequestMapping(value = "display/answer/start/{text}/{color}", method = RequestMethod.POST)
	public void displayAnswer(@PathVariable("text") String text, @PathVariable("color") String color) throws IOException, InterruptedException {
    	this.stopPiWallListener();
    	this.stopDisplayAnswer();
    	//TODO: stream process output to own output, for debugging purposes
    	this.displayAnswerApplication = Optional.of(Runtime.getRuntime().exec(new String[] {"java", "-jar", "project-jfx.jar",  text, color}));
	}

    //TODO: unify the 2 stop methods somehow
    @RequestMapping(value = "display/answer/stop", method = RequestMethod.POST)
	public void stopDisplayAnswer() {
    	try {
    		this.displayAnswerApplication.ifPresent(Process::destroyForcibly);
    	} catch (Exception e) {
    		//TODO: use logging system
    		System.out.println("WARN: exception during closing of pi wall listener");
    	} finally {
    		this.displayAnswerApplication = Optional.empty();			
    	}
	}

    /**
     * Feature: shutdown endpoint.
     */
    @RequestMapping(value = "shutdown", method = RequestMethod.POST)
    public void shutdownServer() {
    	this.stopPiWallListener();
    	this.stopDisplayAnswer();
        SpiquesTileServer.stopServer();
    }

}