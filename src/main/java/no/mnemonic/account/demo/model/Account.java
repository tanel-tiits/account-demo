package no.mnemonic.account.demo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account extends AbstractModel {

    private static final long serialVersionUID = 3565221895815648002L;

    @JsonProperty("id")
    private String externalId; // We use IBAN in this demo

    @JsonProperty
    private String name;

    @JsonProperty
    private BigDecimal availableCash;

    public Account() {} // for Jackson

    public Account(String externalId, String name, BigDecimal availableCash) {

        this.externalId = externalId;
        this.name = name;
        this.availableCash = availableCash;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAvailableCash() {
        return availableCash;
    }

    public static class Builder {

        private String externalId;

        private String name;

        private BigDecimal availableCash;

        public Builder withExternalId(String externalId) {

            this.externalId = externalId;
            return this;
        }

        public Builder withName(String name) {

            this.name = name;
            return this;
        }

        public Builder withAvailableCash(BigDecimal availableCash) {

            this.availableCash = availableCash;
            return this;
        }

        public Account build() {
            return new Account(externalId, name, availableCash);
        }
    }
}
