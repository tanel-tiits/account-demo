package no.mnemonic.account.demo.repository.entity.mapper;

import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;

import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.model.Transaction;
import no.mnemonic.account.demo.repository.entity.TransactionEntity;

public class TransactionEntityMapper {

    @Autowired
    private AccountEntityMapper accountEntityMapper;

    public Transaction toTransaction(TransactionEntity te) {

        ZoneId zone = ZoneId.systemDefault();
        return new Transaction.Builder()
            .withExternalId(te.getExternalId())
            .withCashAmount(te.getCashAmount())
            .withRegisteredTime(te.getRegisteredTime() != null ?
                    te.getRegisteredTime().atZone(zone).toInstant().toEpochMilli() : null)
            .withExecutedTime(te.getExecutedTime() != null ?
                    te.getExecutedTime().atZone(zone).toInstant().toEpochMilli() : null)
            .withSourceAccountExtId(te.getSourceAccount().getExternalId())
            .withDestinationAccountExtId(te.getDestinationAccount().getExternalId())
            .withSuccess(te.isSuccess())
            .build();
    }

    public TransactionEntity fromTransaction(Transaction tx) {

        TransactionEntity te = new TransactionEntity();
        te.setExternalId(tx.getExternalId());
        te.setCashAmount(tx.getCashAmount());
        te.setSourceAccount(accountEntityMapper.fromAccount(new Account.Builder()
                .withExternalId(tx.getSourceAccountExtId()).build()));
        te.setDestinationAccount(accountEntityMapper.fromAccount(new Account.Builder()
                .withExternalId(tx.getDestinationAccountExtId()).build()));
        return te;
    }
}
