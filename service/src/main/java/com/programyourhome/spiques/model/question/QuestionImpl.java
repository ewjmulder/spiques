package com.programyourhome.spiques.model.question;

import java.util.Locale;

public class QuestionImpl implements Question {

	private Locale locale;
	private String declaration;
	
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
