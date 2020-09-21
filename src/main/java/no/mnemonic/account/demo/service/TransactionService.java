package no.mnemonic.account.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.mnemonic.account.demo.exception.InvalidTransactionException;
import no.mnemonic.account.demo.model.Transaction;
import no.mnemonic.account.demo.repository.AccountRepository;
import no.mnemonic.account.demo.repository.TransactionRepository;
import no.mnemonic.account.demo.repository.entity.AccountEntity;
import no.mnemonic.account.demo.repository.entity.TransactionEntity;
import no.mnemonic.account.demo.repository.entity.mapper.TransactionEntityMapper;

@Service
@EnableScheduling
public class TransactionService {

    static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionEntityMapper transactionEntityMapper;


    public List<Transaction> findAllTransactions() {

        return transactionRepository.findAll()
            .stream()
            .map(te -> transactionEntityMapper.toTransaction(te))
            .collect(Collectors.toList());
    }

    public Optional<Transaction> findByExternalId(String extId) {

        Optional<TransactionEntity> te = transactionRepository.findByExternalId(extId);
        if (te.isPresent()) {
            return mapLogAndWrap(te.get());
        }
        logger.info("Didn't find transaction (txExtId={})", extId);
        return Optional.empty();
    }

    @Transactional
    public Transaction saveTransaction(String extId, Transaction tx) {

        validateTransaction(extId, tx);
        Optional<TransactionEntity> te = transactionRepository.findByExternalId(tx.getExternalId());
        if (!te.isPresent()) {
            Optional<AccountEntity> sae = accountRepository.findByExternalId(tx.getSourceAccountExtId());
            if (!sae.isPresent()) {
                throw new InvalidTransactionException("Source account not found!");
            }
            Optional<AccountEntity> dae = accountRepository.findByExternalId(tx.getDestinationAccountExtId());
            if (!dae.isPresent()) {
                throw new InvalidTransactionException("Destination account not found!");
            }
            TransactionEntity toBeStoredTe = transactionEntityMapper.fromTransaction(tx);
            toBeStoredTe.setSourceAccount(sae.get());
            toBeStoredTe.setDestinationAccount(dae.get());
            toBeStoredTe.setRegisteredTime(LocalDateTime.now());
            te = Optional.of(transactionRepository.save(toBeStoredTe));
        }
        Transaction savedTx = transactionEntityMapper.toTransaction(te.get());
        logger.debug("Saved transaction: {}", savedTx);
        return savedTx;
    }

    @Transactional
    @Scheduled(cron = "${scheduler.cron.expression}")
    public synchronized void processUnexecutedTransactions() {

        List<TransactionEntity> teList = transactionRepository.findAllUnexecuted();
        teList.forEach(te -> {
            Transaction tx = transactionEntityMapper.toTransaction(te);
            logger.info("Processing transaction: {}", tx);
            BigDecimal amount = te.getCashAmount();
            AccountEntity sae = te.getSourceAccount();
            AccountEntity dae = te.getDestinationAccount();
            if (sae.getAvailableCash() != null && sae.getAvailableCash().compareTo(amount) >= 0) {
                sae.setAvailableCash(sae.getAvailableCash().subtract(amount));
                dae.setAvailableCash(dae.getAvailableCash().add(amount));
                te.setSuccess(true);
            } else {
                te.setSuccess(false);
            }
            te.setExecutedTime(LocalDateTime.now());
            transactionRepository.save(te);
        });
    }

    protected Optional<Transaction> mapLogAndWrap(TransactionEntity te) {

        Transaction tx = transactionEntityMapper.toTransaction(te);
        logger.debug("Found transaction: {}", tx);
        return Optional.of(tx);
    }

    protected void validateTransaction(String extId, Transaction tx) {

        if (extId == null || tx.getExternalId() == null) {
            throw new InvalidTransactionException("Transaction Id is missing!");
        }
        if (!extId.equalsIgnoreCase(tx.getExternalId())) {
            throw new InvalidTransactionException("Ids from transaction and path variable don't match!");
        }
        if (tx.getSourceAccountExtId() == null
                || StringUtils.isAllBlank(tx.getSourceAccountExtId())) {
            throw new InvalidTransactionException("Source account is missing!");
        }
        if (tx.getDestinationAccountExtId() == null
                || StringUtils.isAllBlank(tx.getDestinationAccountExtId())) {
            throw new InvalidTransactionException("Destination account is missing!");
        }
        if (tx.getSourceAccountExtId().equalsIgnoreCase(tx.getDestinationAccountExtId())) {
            throw new InvalidTransactionException("Source and destination accounts overlap!");
        }
        if (tx.getCashAmount() == null) {
            throw new InvalidTransactionException("Transferrable cash amount is missing!");
        }
        if (tx.getCashAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transferrable cash amount is invalid!");
        }
    }
}
