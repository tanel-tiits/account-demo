package no.mnemonic.account.demo.repository.entity.mapper;

import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.repository.entity.AccountEntity;

public class AccountEntityMapper {

    public Account toAccount(AccountEntity ae) {

        return new Account.Builder()
            .withExternalId(ae.getExternalId())
            .withName(ae.getName())
            .withAvailableCash(ae.getAvailableCash())
            .build();
    }

    public AccountEntity fromAccount(Account a) {

        AccountEntity ae = new AccountEntity();
        ae.setExternalId(a.getExternalId());
        ae.setName(a.getName());
        ae.setAvailableCash(a.getAvailableCash());
        return ae;
    }
}
