package com.programyourhome.spiques.master.model.question;

import java.util.Locale;

public interface Question {

	QuestionType getType();
	
	Locale getLocale();
	
	String getDeclaration();
	
}
