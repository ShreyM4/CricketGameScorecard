package com.cricket.scorecard.exception;

/**
 * Custom Unchecked Runtime Exception thrown when an illegal match operation is attempted
 * (e.g. attempting to record a ball delivery on a match that is already COMPLETED).
 * 
 * DEVELOPER NOTES:
 * - Extends RuntimeException (Unchecked Exception).
 * - Caught centrally by GlobalExceptionHandler to return an HTTP 400 BAD REQUEST status.
 */
public class InvalidMatchStateException extends RuntimeException {

    /**
     * Constructs exception with detailed business state error message.
     * 
     * @param message Descriptive error message explaining why state transition is invalid.
     */
    public InvalidMatchStateException(String message) {
        super(message);
    }
}

