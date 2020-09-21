package no.mnemonic.account.demo.exception;

public class InvalidTransactionException extends RuntimeException {

    private static final long serialVersionUID = 3798948049266772626L;

    public InvalidTransactionException(String message) {
        super(message);
    }
}
