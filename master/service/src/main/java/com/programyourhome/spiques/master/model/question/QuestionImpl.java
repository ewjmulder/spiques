package com.programyourhome.spiques.master.model.question;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.programyourhome.spiques.master.model.question.Question;

@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// TODO: Can the type come from the enum?
@JsonSubTypes({
    @Type(value = MultipleChoiceQuestionImpl.class)
})
public abstract class QuestionImpl implements Question {

	private Locale locale;
	private String declaration;
	
	protected QuestionImpl() {
		// For Jackson.
	}
	
	public QuestionImpl(Locale locale, String declaration) {
		this.locale = locale;
		this.declaration = declaration;
	}
	
	@Override
	public Locale getLocale() {
		return this.locale;
	}

	
	@Override
	public String getDeclaration() {
		return this.declaration;
	}	
	
}
