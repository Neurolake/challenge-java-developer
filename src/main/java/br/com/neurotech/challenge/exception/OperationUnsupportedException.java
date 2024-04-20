package br.com.neurotech.challenge.exception;

public class OperationUnsupportedException extends RuntimeException {
    public OperationUnsupportedException(String message) {
        super(message);
    }
}
