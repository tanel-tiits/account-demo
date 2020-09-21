package no.mnemonic.account.demo.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.mnemonic.account.demo.exception.InvalidTransactionException;
import no.mnemonic.account.demo.model.Transaction;

class TransactionServiceTest {

    static final String ACCOUNT_EXT_ID_1 = "NO0312340000001";
    static final String ACCOUNT_EXT_ID_2 = "NO7312340000002";
    static final String TRANS_EXT_ID = "ee73c29d-eca2-4cdf-b240-c035fdc765e6";
    static final String AMOUNT = "100.0";

    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = spy(TransactionService.class);
    }

    @Test
    void testValidateTransaction_isValid() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANS_EXT_ID, tx);
    }

    @Test
    void testValidateTransaction_extIdsDontMatch() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction("ABC", tx));
    }

    @Test
    void testValidateTransaction_extIdIsMissing1() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(null)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_extIdIsMissing2() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(null, tx));
    }

    @Test
    void testValidateTransaction_sourceAccountExtIdIsMissing() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(null)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_sourceAccountExtIdIsEmpty() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId("")
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_destinationAccountExtIdIsMissing() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(null)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_destinationAccountExtIdIsEmpty() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId("")
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_sourceAndDestinationAccountExtIdsOverlap() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_1)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_cashAmountIsMissing() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(null)
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }

    @Test
    void testValidateTransaction_cashAmountIsNegative() throws Exception {

        Transaction tx = new Transaction.Builder()
                .withExternalId(TRANS_EXT_ID)
                .withCashAmount(new BigDecimal("-200.0"))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        assertThrows(InvalidTransactionException.class, () -> transactionService.validateTransaction(TRANS_EXT_ID, tx));
    }
}
