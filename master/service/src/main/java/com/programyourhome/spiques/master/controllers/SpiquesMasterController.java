package com.programyourhome.spiques.master.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programyourhome.spiques.master.model.Position;
import com.programyourhome.spiques.master.model.Quiz;
import com.programyourhome.spiques.master.model.QuizImpl;
import com.programyourhome.spiques.master.model.Tile;
import com.programyourhome.spiques.master.model.TileImpl;
import com.programyourhome.spiques.master.model.question.MultipleChoiceQuestion;

@RestController
@RequestMapping("spiques")
public class SpiquesMasterController {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${tiles.ips}")
	private String[] tileIps;

	// TODO: Is this mapping really useful? Do we need the position separate at any time?
	private Map<Position, Tile> tiles;
	
	private Map<String, Quiz> quizzes;

	private List<String> colors;

	private RestTemplate restTemplate;
	
	public SpiquesMasterController() {
		restTemplate = new RestTemplate();
		colors = Arrays.asList("RED", "GREEN", "BLUE", "YELLOW");
	}
	
	@PostConstruct
	public void init() {
		this.tiles = com.codepoetics.protonpack.StreamUtils
				.zip(Arrays.stream(Position.values()), Arrays.stream(this.tileIps), (position, ip) -> new TileImpl(position, ip))
				.collect(Collectors.toMap(Tile::getPosition, tile -> tile));
		//TODO: get from config file
		this.quizzes = Arrays.stream(new File("./src/test/resources/quizzes").listFiles())
				.filter(File::isDirectory)
				.map(this::parseQuiz)
				.collect(Collectors.toMap(Quiz::getId, quiz -> quiz));
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
    public Collection<Tile> getTiles() {
        return tiles.values();
    }

    @RequestMapping(value = "quizzes", method = RequestMethod.GET)
    public Collection<Quiz> getQuizzes() {
    	return quizzes.values();
    }

    @RequestMapping(value = "quizzes/{id}", method = RequestMethod.GET)
    public Quiz getQuizzes(@PathVariable("id") String id) {
    	return quizzes.get(id);
    }
    
    // TODO: make nicer
    private void callTilesEndpoint(HttpMethod httpMethod, String path) {
    	tiles.values().forEach(tile -> restTemplate.exchange("http://" + tile.getIp() + ":8181/spiques/" + path, httpMethod, HttpEntity.EMPTY, String.class));
    }

    private Void callTileEndpoint(HttpMethod httpMethod, Tile tile, String path) {
    	System.out.println("About to invoke:    " + "http://" + tile.getIp() + ":8181/spiques/" + path);
    	restTemplate.exchange("http://" + tile.getIp() + ":8181/spiques/" + path, httpMethod, HttpEntity.EMPTY, String.class);
    	return null;
    }

    private void startTileListeners() {
    	callTilesEndpoint(HttpMethod.POST, "listener/start");
    }

    private void stopTileListeners() {
    	callTilesEndpoint(HttpMethod.POST, "listener/stop");
    }

    @RequestMapping(value = "testHeli", method = RequestMethod.POST)
    public void testHeli()  {
    	new Thread(() -> {
    		try {
	        	startTileListeners();
	        	Process process = Runtime.getRuntime().exec("avconv -re -i /home/emulder/prive/SPiQues/PiWall/helicopter.avi -vcodec copy -acodec copy -f mpegts udp://239.0.1.23:1234");
	        	process.waitFor();
	        	//TODO: config value - buffer waiting time is about 10 secs
	        	Thread.sleep(8 * 1000);
	        	stopTileListeners();
    		} catch (Exception e) {
    			//TODO: use logger
    			e.printStackTrace();
    		}
    	}).start();
    }
    
    @RequestMapping(value = "quizzes/{id}/start", method = RequestMethod.POST)
    public void startQuiz(@PathVariable("id") String id)  {
    	this.startQuiz(this.quizzes.get(id));
    }
    
    private void startQuiz(Quiz quiz) {
    	// TODO: How can this be solved in a nice way? -> check on type and case is not very cool
    	MultipleChoiceQuestion q1 = (MultipleChoiceQuestion) quiz.getQuestions().iterator().next();
    	com.codepoetics.protonpack.StreamUtils
				.zip(tiles.values().stream(), q1.getAnswers().stream(), colors.stream(), (tile, answer, color) -> callTileEndpoint(HttpMethod.POST, tile, "display/answer/" + answer + "/" + color))
// TODO: fix, getting list is dummy but needed to evaluate the stream
				.collect(Collectors.toList());
    }

}