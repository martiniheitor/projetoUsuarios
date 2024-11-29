// Exceção personalizada para lidar com erros relacionados à validação de CPF.
package com.heitor.workshopmysql.resources.exceptions;

public class InvalidCpfException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidCpfException(String message) {
		super(message);
	}
	
}