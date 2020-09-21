package no.mnemonic.account.demo.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3714974358122454723L;

    public NotFoundException(String message) {
        super(message);
    }
}
