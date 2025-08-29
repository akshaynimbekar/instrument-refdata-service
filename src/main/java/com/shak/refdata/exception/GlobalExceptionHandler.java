package com.shak.refdata.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
//	@ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//    }
	
	// Handle ResourceNotFoundException
			@ExceptionHandler(ResourceNotFoundException.class)
			public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
				ApiError error = new ApiError(
						HttpStatus.NOT_FOUND.value(),
						"Not Found",
						ex.getMessage(),
						request.getRequestURI()
				);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			}

	// Handle Validation Errors
			@ExceptionHandler(MethodArgumentNotValidException.class)
			public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
				String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
				ApiError error = new ApiError(
						HttpStatus.BAD_REQUEST.value(),
						"Validation Error",
						message,
						request.getRequestURI()
				);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
			}

			// Handle Generic Exceptions
			@ExceptionHandler(Exception.class)
			public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
				ApiError error = new ApiError(
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						"Internal Server Error",
						ex.getMessage(),
						request.getRequestURI()
				);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
			}
    
}
