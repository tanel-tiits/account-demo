package no.mnemonic.account.demo.service;

import static org.mockito.Mockito.spy;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import no.mnemonic.account.demo.exception.InvalidTransactionException;
import no.mnemonic.account.demo.model.Transaction;

public class TransactionServiceTest {


    static final String ACCOUNT_EXT_ID_1 = "NO0312340000001";
    static final String ACCOUNT_EXT_ID_2 = "NO7312340000002";
    static final String TRANSACTION_EXT_ID = "ee73c29d-eca2-4cdf-b240-c035fdc765e6";
    static final String AMOUNT = "100.0";

    TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = spy(TransactionService.class);
    }

    @Test
    public void testValidateTransaction_isValid() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_extIdsDontMatch() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction("ABC", t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_extIdIsMissing1() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(null)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_extIdIsMissing2() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(null, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_sourceAccountExtIdIsMissing() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(null)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_sourceAccountExtIdIsEmpty() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId("")
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_destinationAccountExtIdIsMissing() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(null)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_destinationAccountExtIdIsEmpty() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId("")
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_sourceAndDestinationAccountExtIdsOverlap() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal(AMOUNT))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_1)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_cashAmountIsMissing() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(null)
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }

    @Test(expected = InvalidTransactionException.class)
    public void testValidateTransaction_cashAmountIsNegative() throws Exception {

        Transaction t = new Transaction.Builder()
                .withExternalId(TRANSACTION_EXT_ID)
                .withCashAmount(new BigDecimal("-200.0"))
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        transactionService.validateTransaction(TRANSACTION_EXT_ID, t);
    }
}
