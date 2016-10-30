package com.programyourhome.spiques.master.model.question;

import java.util.List;

public class MultipleChoiceQuestionImpl extends QuestionImpl implements MultipleChoiceQuestion {

	private List<String> answers;
	private String rightAnswerColor;
	
	private MultipleChoiceQuestionImpl() {
		// For Jackson.
	}
	
//	public MultipleChoiceQuestionImpl(Locale locale, String declaration, String ... answers) {
//		super(locale, declaration);
//		this.answers = Arrays.asList(answers);
//	}
	
	@Override
	public List<String> getAnswers() {
		return answers;
	}
	
	@Override
	public String getRightAnswerColor() {
		return this.rightAnswerColor;
	}
	
}
