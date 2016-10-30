package com.programyourhome.spiques.master.model;

import java.util.List;

import com.programyourhome.spiques.master.model.question.Question;

public interface Quiz {

	String getId();
	
	String getName();
	
	default List<? extends Question> getQuestions() {
		return getQuestions(Order.DEFAULT);
	}
	
	List<? extends Question> getQuestions(Order order);
	
}
