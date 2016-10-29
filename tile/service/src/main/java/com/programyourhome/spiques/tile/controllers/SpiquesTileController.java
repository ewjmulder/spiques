package com.programyourhome.spiques.tile.controllers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public SpiquesTileController() {
		this.currectPiWallListener = Optional.empty();
	}
	
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "listener/start", method = RequestMethod.POST)
    public void startPiWallListener() throws IOException, InterruptedException {
    	this.stopPiWallListener();
    	Process process = Runtime.getRuntime().exec("pwomxplayer --tile-code=" + tileCode + " udp://239.0.1.23:1234?buffer_size=1200000B");
    	process.waitFor();
    	System.out.println("exit: " + process.exitValue());
    	System.out.println("output stream: " + StreamUtils.copyToString(process.getInputStream(), Charset.forName("UTF-8")));
    	System.out.println("error stream: " + StreamUtils.copyToString(process.getErrorStream(), Charset.forName("UTF-8")));
    }

    @RequestMapping(value = "listener/stop", method = RequestMethod.POST)
	public void stopPiWallListener() {
		try {
			if (this.currectPiWallListener.isPresent()) {
				this.currectPiWallListener.get().destroyForcibly();
			}
		} catch (Exception e) {
			//TODO: use logging system
			System.out.println("WARN: exception during closing of pi wall listener");
		} finally {
			this.currectPiWallListener = Optional.empty();			
		}
	}

}