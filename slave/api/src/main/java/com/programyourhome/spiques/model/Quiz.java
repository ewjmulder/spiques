package com.programyourhome.spiques.model;

import com.programyourhome.spiques.model.question.Question;

public interface Quiz {

	String getName();
	
	Iterable<Question> getQuestions(Order order);
	
}
