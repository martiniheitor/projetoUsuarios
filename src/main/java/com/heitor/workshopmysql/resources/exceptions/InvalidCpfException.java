package com.heitor.workshopmysql.resources.exceptions;

public class InvalidCpfException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidCpfException(String message) {
		super(message);
	}
	
}