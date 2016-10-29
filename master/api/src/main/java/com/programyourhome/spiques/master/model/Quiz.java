package com.programyourhome.spiques.master.model;

import com.programyourhome.spiques.master.model.question.Question;

public interface Quiz {

	String getName();
	
	default Iterable<? extends Question> getQuestions() {
		return getQuestions(Order.DEFAULT);
	}
	
	Iterable<? extends Question> getQuestions(Order order);
	
}
