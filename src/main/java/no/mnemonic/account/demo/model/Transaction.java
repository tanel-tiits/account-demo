package no.mnemonic.account.demo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction extends AbstractModel {

    private static final long serialVersionUID = 2198625217877428784L;

    @JsonProperty("id")
    private String externalId; // We use UUID in this demo

    @JsonProperty
    private Long registeredTime;

    @JsonProperty
    private Long executedTime;

    @JsonProperty
    private Boolean success;

    @JsonProperty
    private BigDecimal cashAmount;

    @JsonProperty("sourceAccount")
    private String sourceAccountExtId; // Source account IBAN

    @JsonProperty("destinationAccount")
    private String destinationAccountExtId; // Destination account IBAN

    public Transaction() {} // for Jackson

    public Transaction(String externalId, Long registeredTime, Long executedTime, Boolean success,
            BigDecimal cashAmount, String sourceAccountExtId, String destinationAccountExtId) {

        this.externalId = externalId;
        this.registeredTime = registeredTime;
        this.executedTime = executedTime;
        this.success = success;
        this.cashAmount = cashAmount;
        this.sourceAccountExtId = sourceAccountExtId;
        this.destinationAccountExtId = destinationAccountExtId;
    }

    public String getExternalId() {
        return externalId;
    }

    public Long getRegisteredTime() {
        return registeredTime;
    }

    public Long getExecutedTime() {
        return executedTime;
    }

    public Boolean isSuccess() {
        return success;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public String getSourceAccountExtId() {
        return sourceAccountExtId;
    }

    public String getDestinationAccountExtId() {
        return destinationAccountExtId;
    }

    public static class Builder {

        private String externalId;
        private Long registeredTime;
        private Long executedTime;
        private Boolean success;
        private BigDecimal cashAmount;
        private String sourceAccountExtId;
        private String destinationAccountExtId;

        public Builder withExternalId(String externalId) {

            this.externalId = externalId;
            return this;
        }

        public Builder withRegisteredTime(Long registeredTime) {

            this.registeredTime = registeredTime;
            return this;
        }

        public Builder withExecutedTime(Long executedTime) {

            this.executedTime = executedTime;
            return this;
        }

        public Builder withSuccess(Boolean success) {

            this.success = success;
            return this;
        }

        public Builder withCashAmount(BigDecimal cashAmount) {

            this.cashAmount = cashAmount;
            return this;
        }

        public Builder withSourceAccountExtId(String sourceAccountExtId) {

            this.sourceAccountExtId = sourceAccountExtId;
            return this;
        }

        public Builder withDestinationAccountExtId(String destinationAccountExtId) {

            this.destinationAccountExtId = destinationAccountExtId;
            return this;
        }

        public Transaction build() {

            return new Transaction(externalId, registeredTime, executedTime, success, cashAmount,
                    sourceAccountExtId, destinationAccountExtId);
        }
    }
}
