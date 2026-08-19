package com.cricket.scorecard.exception;

/**
 * Custom Unchecked Runtime Exception thrown when a requested domain resource (Player / Match)
 * is not found in the database.
 * 
 * DEVELOPER NOTES:
 * - Extends RuntimeException (Unchecked Exception).
 * - Caught centrally by GlobalExceptionHandler to return an HTTP 404 NOT FOUND status.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs exception with custom error message.
     * 
     * @param message Descriptive error message (e.g., "Player not found with id: 123").
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

