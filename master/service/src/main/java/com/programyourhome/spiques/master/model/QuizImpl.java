package com.programyourhome.spiques.master.model;

import java.util.Arrays;

import com.programyourhome.spiques.master.model.question.QuestionImpl;

public class QuizImpl implements Quiz {

	private String id;
	private String name;
	private QuestionImpl[] questions;
	
	private QuizImpl() {
		// For Jackson.
	}
	
//	public QuizImpl(String name, List<QuestionImpl> questions) {
//		this.name = name;
//		this.questions = questions;
//	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Iterable<QuestionImpl> getQuestions(Order order) {
		return Arrays.asList(this.questions);
	}
	
}
