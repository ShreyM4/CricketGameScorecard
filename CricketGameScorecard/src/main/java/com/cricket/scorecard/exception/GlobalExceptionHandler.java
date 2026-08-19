package com.cricket.scorecard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized Global Exception Handler Component for the REST API.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @RestControllerAdvice: Combines @ControllerAdvice and @ResponseBody. Intercepts runtime exceptions
 *   thrown across all @RestController classes in the application and automatically serializes the returned
 *   error response maps directly into JSON bodies.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException (e.g. Player ID or Match ID not found in database).
     * Returns HTTP 404 NOT FOUND status code with timestamp and error description.
     * 
     * @param ex Caught ResourceNotFoundException.
     * @return ResponseEntity with structured JSON error payload.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidMatchStateException (e.g. attempting to record a ball on a completed match).
     * Returns HTTP 400 BAD REQUEST status code.
     * 
     * @param ex Caught InvalidMatchStateException.
     * @return ResponseEntity with structured JSON error payload.
     */
    @ExceptionHandler(InvalidMatchStateException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidMatchState(InvalidMatchStateException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles explicit Java argument validation errors (IllegalArgumentException).
     * Returns HTTP 400 BAD REQUEST status code with timestamp and descriptive error message.
     * 
     * @param ex Caught IllegalArgumentException.
     * @return ResponseEntity containing JSON error body.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    /**
     * Fallback Exception Handler for any unexpected uncaught exceptions in the application.
     * Returns HTTP 500 INTERNAL SERVER ERROR status code.
     * 
     * @param ex Caught generic Exception.
     * @return ResponseEntity with internal error payload.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

