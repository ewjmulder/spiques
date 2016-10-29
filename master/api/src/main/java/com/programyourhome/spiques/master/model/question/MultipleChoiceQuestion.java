package com.programyourhome.spiques.master.model.question;

import java.util.List;

public interface MultipleChoiceQuestion extends Question {

	default QuestionType getType() {
		return QuestionType.MULTIPLE_CHOICE;
	}
	
	List<String> getAnswers();
	
}
