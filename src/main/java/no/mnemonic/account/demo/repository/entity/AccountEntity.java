package no.mnemonic.account.demo.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity extends AbstractEntity {

    private static final long serialVersionUID = -8290411317933560734L;

    @Column(name = "EXTERNAL_ID", unique = true)
    private String externalId; // We use IBAN in this demo

    @Column(name = "NAME")
    private String name;

    @Column(name = "AVAILABLE_CASH")
    private BigDecimal availableCash;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(BigDecimal availableCash) {
        this.availableCash = availableCash;
    }
}
