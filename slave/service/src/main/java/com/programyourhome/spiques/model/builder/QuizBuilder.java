package com.programyourhome.spiques.model.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.programyourhome.spiques.model.Quiz;
import com.programyourhome.spiques.model.QuizImpl;
import com.programyourhome.spiques.model.question.MultipleChoiceQuestionImpl;
import com.programyourhome.spiques.model.question.Question;

public class QuizBuilder {

	private String name;
	private List<Question> questions;
	
	public QuizBuilder(String name) {
		this.name = name;
		this.questions = new ArrayList<>();
	}
	
	public QuizBuilder addMultipleChoiceQuestion(Locale locale, String declaration, String ... answers) {
		this.questions.add(new MultipleChoiceQuestionImpl(locale, declaration, answers));
		return this;
	}
	
	public Quiz build() {
		return new QuizImpl(name, questions);
	}
	
}
