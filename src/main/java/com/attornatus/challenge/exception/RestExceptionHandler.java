// controlador de ResponseStatusException
// serve para ocultar o stackTrace desse tipo de exceção e retornar apenas a mensagem com o http status

package com.attornatus.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
		String errorMessage = ex.getReason();
		HttpStatus status = ex.getStatus();
		return new ResponseEntity<>(errorMessage, status);
	}
	
}
