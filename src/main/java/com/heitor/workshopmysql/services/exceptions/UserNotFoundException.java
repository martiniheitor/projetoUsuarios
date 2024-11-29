//Essa classe representa uma exceção personalizada, lançada quando o usuário solicitado não é encontrado no sistema.
package com.heitor.workshopmysql.services.exceptions;

public class UserNotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
        super(message);
    }
    
}