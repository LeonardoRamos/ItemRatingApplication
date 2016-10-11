package com.uff.item.rating.application.exception;

public class InvalidPredictionStrategyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidPredictionStrategyException(String message) {
        super(message);
    }

    public InvalidPredictionStrategyException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}