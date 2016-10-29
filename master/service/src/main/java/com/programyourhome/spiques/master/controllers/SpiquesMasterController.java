package com.programyourhome.spiques.master.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programyourhome.spiques.master.model.Position;
import com.programyourhome.spiques.master.model.Quiz;
import com.programyourhome.spiques.master.model.QuizImpl;
import com.programyourhome.spiques.master.model.Tile;
import com.programyourhome.spiques.master.model.TileImpl;

@RestController
@RequestMapping("spiques")
public class SpiquesMasterController {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${tiles.ips}")
	private String[] tileIps;

	private List<Tile> tiles;
	
	private List<Quiz> quizzes;
	
	@PostConstruct
	public void init() {
		this.tiles = com.codepoetics.protonpack.StreamUtils
				.zip(Arrays.stream(Position.values()), Arrays.stream(this.tileIps), (position, ip) -> new TileImpl(position, ip))
				.collect(Collectors.toList());
		//TODO: get from config file
		this.quizzes = Arrays.stream(new File("./src/test/resources/quizzes").listFiles())
				.filter(File::isDirectory)
				.map(this::parseQuiz)
				.collect(Collectors.toList());
	}
	
	private Quiz parseQuiz(File quizDirectory) {
		try {
			return objectMapper.readValue(new File(quizDirectory, "quiz.json"), QuizImpl.class);
		} catch (IOException e) {
			throw new IllegalStateException("IOException during quiz parsing.", e);
		}
	}
	
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "tiles", method = RequestMethod.GET)
    public List<Tile> getTiles() {
        return tiles;
    }

    @RequestMapping(value = "quizzes", method = RequestMethod.GET)
    public List<Quiz> getQuizzes() {
    	return quizzes;
    }
    
    @RequestMapping(value = "testscp", method = RequestMethod.GET)
    public String testScp() throws IOException, InterruptedException {
    	Process process = Runtime.getRuntime().exec("scp /home/emulder/test.txt pi@192.168.4.101:~");
    	process.waitFor();
    	Process process2 = Runtime.getRuntime().exec("pwd");
    	process2.waitFor();
    	System.out.println("exit: " + process2.exitValue());
    	System.out.println("output stream: " + StreamUtils.copyToString(process2.getInputStream(), Charset.forName("UTF-8")));
        return "ok";
    }

}