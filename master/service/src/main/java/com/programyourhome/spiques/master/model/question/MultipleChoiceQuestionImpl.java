package com.programyourhome.spiques.master.model.question;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.programyourhome.spiques.master.model.question.MultipleChoiceQuestion;

public class MultipleChoiceQuestionImpl extends QuestionImpl implements MultipleChoiceQuestion {

	private List<String> answers;
	
	private MultipleChoiceQuestionImpl() {
		// For Jackson.
	}
	
	public MultipleChoiceQuestionImpl(Locale locale, String declaration, String ... answers) {
		super(locale, declaration);
		this.answers = Arrays.asList(answers);
	}
	
	@Override
	public Iterable<String> getAnswers() {
		return answers;
	}
	
}
