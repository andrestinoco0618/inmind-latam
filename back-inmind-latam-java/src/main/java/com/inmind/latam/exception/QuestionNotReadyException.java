package com.inmind.latam.exception;

public class QuestionNotReadyException extends Exception{

	private static final long serialVersionUID = 1L;

	public QuestionNotReadyException(String message) {
		super(message);
	}

}