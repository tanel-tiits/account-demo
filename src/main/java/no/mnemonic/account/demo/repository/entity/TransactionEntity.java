package no.mnemonic.account.demo.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity extends AbstractEntity {

    private static final long serialVersionUID = -2952252163726986273L;

    @Column(name = "EXTERNAL_ID", unique = true)
    private String externalId; // We use UUID in this demo

    @Column(name = "REGISTERED_TIME")
    private LocalDateTime registeredTime;

    @Column(name = "EXECUTED_TIME")
    private LocalDateTime executedTime;

    @Column(name = "SUCCESS")
    private Boolean success;

    @Column(name = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @ManyToOne
    @JoinColumn(name = "SOURCE_ACCOUNT_ID")
    private AccountEntity sourceAccount;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_ACCOUNT_ID")
    private AccountEntity destinationAccount;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public LocalDateTime getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(LocalDateTime registeredTime) {
        this.registeredTime = registeredTime;
    }

    public LocalDateTime getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(LocalDateTime executedTime) {
        this.executedTime = executedTime;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public AccountEntity getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(AccountEntity sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public AccountEntity getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(AccountEntity destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
