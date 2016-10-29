package com.programyourhome.spiques.model;

import com.programyourhome.spiques.model.question.Question;

public class QuizImpl implements Quiz {

	private String name;
	private Iterable<Question> questions;
	
	public QuizImpl(String name, Iterable<Question> questions) {
		this.name = name;
		this.questions = questions;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Iterable<Question> getQuestions(Order order) {
		return this.questions;
	}
	
}
