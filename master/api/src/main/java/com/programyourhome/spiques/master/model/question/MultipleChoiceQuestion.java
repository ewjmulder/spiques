package com.programyourhome.spiques.master.model.question;

public interface MultipleChoiceQuestion extends Question {

	default QuestionType getType() {
		return QuestionType.MULTIPLE_CHOICE;
	}
	
	Iterable<String> getAnswers();
	
}
