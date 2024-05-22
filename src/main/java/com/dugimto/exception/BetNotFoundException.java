package com.dugimto.exception;

public class BetNotFoundException extends RuntimeException {
    public BetNotFoundException() {
        super();
    }

    public BetNotFoundException(Long id) {
        super("Bet not found with id: " + id);
    }

    public BetNotFoundException(String message) {
        super(message);
    }

    public BetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BetNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BetNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
