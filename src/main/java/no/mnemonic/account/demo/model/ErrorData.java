package no.mnemonic.account.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorData implements Serializable {

    private static final long serialVersionUID = 7500739377391135440L;

    @JsonProperty
    private String error;

    @JsonProperty
    private String message;

    public ErrorData() {} // for Jackson

    public ErrorData(String error, String message) {

        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
