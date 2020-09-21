package no.mnemonic.account.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.repository.AccountRepository;
import no.mnemonic.account.demo.repository.entity.AccountEntity;
import no.mnemonic.account.demo.repository.entity.mapper.AccountEntityMapper;

@Service
public class AccountService {

    static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountEntityMapper accountEntityMapper;

    public List<Account> findAllAccounts() {

        return accountRepository.findAll()
            .stream()
            .map(ae -> accountEntityMapper.toAccount(ae))
            .collect(Collectors.toList());
    }

    public Optional<Account> findByExternalId(String extId) {

        Optional<AccountEntity> ae = accountRepository.findByExternalId(extId);
        if (ae.isPresent()) {
            return mapLogAndWrap(ae.get());
        }
        logger.info("Didn't find account (accExtId={})", extId);
        return Optional.empty();
    }

    protected Optional<Account> mapLogAndWrap(AccountEntity ae) {

        Account a = accountEntityMapper.toAccount(ae);
        logger.info("Found account: {}", a);
        return Optional.of(a);
    }
}
