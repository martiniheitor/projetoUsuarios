// Classe responsável por centralizar o tratamento de exceções na aplicação.
package com.heitor.workshopmysql.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.heitor.workshopmysql.services.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Método que trata exceções de CPF inválido, retornando status HTTP 400 (Bad Request).
    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<String> handleInvalidCpf(InvalidCpfException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Método que trata exceções de "usuário não encontrado", retornando status HTTP 404 (Not Found).
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Método que trata exceções genéricas, retornando status HTTP 500 (Internal Server Error).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
    }
}