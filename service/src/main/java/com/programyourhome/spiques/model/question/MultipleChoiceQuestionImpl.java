package com.programyourhome.spiques.model.question;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MultipleChoiceQuestionImpl extends QuestionImpl implements MultipleChoiceQuestion {

	private List<String> answers;
	
	public MultipleChoiceQuestionImpl(Locale locale, String declaration, String ... answers) {
		super(locale, declaration);
		this.answers = Arrays.asList(answers);
	}
	
	@Override
	public Iterable<String> getAnswers() {
		return answers;
	}
	
}
