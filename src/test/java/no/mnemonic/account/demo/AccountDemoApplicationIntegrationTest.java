package no.mnemonic.account.demo;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.mnemonic.account.demo.config.ApplicationTestConfig;
import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.model.ErrorData;
import no.mnemonic.account.demo.model.Transaction;
import no.mnemonic.account.demo.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ApplicationTestConfig.class})
@TestPropertySource(locations = {"classpath:application-test.properties"})
class AccountDemoApplicationIntegrationTest {

    static final String ACCOUNT_EXT_ID_1 = "NO0312340000001"; // account has 1000.0 initially
    static final String ACCOUNT_EXT_ID_2 = "NO7312340000002"; // account has 2000.0 initially

    static final String TRANSACTION_EXT_ID_1 = "ee73c29d-eca2-4cdf-b240-c035fdc765e6";
    static final String TRANSACTION_EXT_ID_2 = "10e157d1-a170-418e-8754-50d67e65c5c7";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TransactionService txService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAllAccounts() throws Exception {

        MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get(ApiConstants.ACCOUNT_CONTROLLER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        List<Account> accounts = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Account>>() {});
        assertNotNull(accounts);
        assertFalse(accounts.isEmpty());
    }

    @Test
    void testAccountNotFound() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ApiConstants.ACCOUNT_CONTROLLER_ENDPOINT + "/{extId}", "XYZ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

        ErrorData errorData = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorData.class);
        assertNotNull(errorData);
    }

    @Test
    void testGetAllTransactions() throws Exception {

        MvcResult result = mockMvc
            .perform(MockMvcRequestBuilders
                    .get(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        List<Transaction> transactions = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Transaction>>() {});
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
    }

    @Test
    void testTransactionNotFound() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT + "/{extId}", "XYZ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

        ErrorData errorData = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorData.class);
        assertNotNull(errorData);
    }

    @Test
    void testSuccessfulTransaction() throws Exception {

        BigDecimal amount = new BigDecimal("500.0");

        Account srcAccountBefore = getAccountByExternalId(ACCOUNT_EXT_ID_1);
        Account dstAccountBefore = getAccountByExternalId(ACCOUNT_EXT_ID_2);

        assertNotNull(srcAccountBefore);
        assertNotNull(dstAccountBefore);
        assertNotNull(srcAccountBefore.getAvailableCash());
        assertNotNull(dstAccountBefore.getAvailableCash());
        assertTrue(srcAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(dstAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);

        Transaction tx = new Transaction.Builder()
                .withCashAmount(amount)
                .withExternalId(TRANSACTION_EXT_ID_1)
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        MvcResult resultBeforeProcessing = mockMvc.perform(MockMvcRequestBuilders
                .put(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT + "/{extId}", TRANSACTION_EXT_ID_1)
                .content(objectMapper.writeValueAsString(tx))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        Transaction txBeforeProcessing = objectMapper.readValue(resultBeforeProcessing.getResponse().getContentAsString(),
                Transaction.class);

        assertNotNull(txBeforeProcessing);
        assertEquals(TRANSACTION_EXT_ID_1, txBeforeProcessing.getExternalId());
        assertNotNull(txBeforeProcessing.getRegisteredTime());
        assertNull(txBeforeProcessing.getExecutedTime());
        assertNull(txBeforeProcessing.isSuccess());

        txService.processUnexecutedTransactions();

        MvcResult resultAfterProcessing = mockMvc.perform(MockMvcRequestBuilders
                .get(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT + "/{extId}", TRANSACTION_EXT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        Transaction txAfterProcessing = objectMapper.readValue(resultAfterProcessing.getResponse().getContentAsString(),
                Transaction.class);

        assertNotNull(txAfterProcessing);
        assertEquals(TRANSACTION_EXT_ID_1, txAfterProcessing.getExternalId());
        assertNotNull(txAfterProcessing.getRegisteredTime());
        assertNotNull(txAfterProcessing.getExecutedTime());
        assertEquals(Boolean.TRUE, txAfterProcessing.isSuccess());

        Account srcAccountAfter = getAccountByExternalId(ACCOUNT_EXT_ID_1);
        Account dstAccountAfter = getAccountByExternalId(ACCOUNT_EXT_ID_2);

        assertEquals(srcAccountBefore.getAvailableCash().subtract(amount), srcAccountAfter.getAvailableCash());
        assertEquals(dstAccountBefore.getAvailableCash().add(amount), dstAccountAfter.getAvailableCash());
        assertTrue(srcAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(dstAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testUnsuccessfulTransaction() throws Exception {

        BigDecimal amount = new BigDecimal("1500.0");

        Account srcAccountBefore = getAccountByExternalId(ACCOUNT_EXT_ID_1);
        Account dstAccountBefore = getAccountByExternalId(ACCOUNT_EXT_ID_2);

        assertNotNull(srcAccountBefore);
        assertNotNull(dstAccountBefore);
        assertNotNull(srcAccountBefore.getAvailableCash());
        assertNotNull(dstAccountBefore.getAvailableCash());
        assertTrue(srcAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(dstAccountBefore.getAvailableCash().compareTo(BigDecimal.ZERO) >= 0);

        Transaction tx = new Transaction.Builder()
                .withCashAmount(amount)
                .withExternalId(TRANSACTION_EXT_ID_2)
                .withSourceAccountExtId(ACCOUNT_EXT_ID_1)
                .withDestinationAccountExtId(ACCOUNT_EXT_ID_2)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                .put(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT + "/{extId}", TRANSACTION_EXT_ID_2)
                .content(objectMapper.writeValueAsString(tx))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        txService.processUnexecutedTransactions();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT + "/{extId}", TRANSACTION_EXT_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        Transaction txFromResult = objectMapper.readValue(result.getResponse().getContentAsString(), Transaction.class);

        assertNotNull(txFromResult);
        assertEquals(TRANSACTION_EXT_ID_2, txFromResult.getExternalId());
        assertEquals(Boolean.FALSE, txFromResult.isSuccess());

        Account srcAccountAfter = getAccountByExternalId(ACCOUNT_EXT_ID_1);
        Account dstAccountAfter = getAccountByExternalId(ACCOUNT_EXT_ID_2);

        assertEquals(srcAccountBefore.getAvailableCash(), srcAccountAfter.getAvailableCash());
        assertEquals(dstAccountBefore.getAvailableCash(), dstAccountAfter.getAvailableCash());
    }

    protected Account getAccountByExternalId(String accExtId) throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ApiConstants.ACCOUNT_CONTROLLER_ENDPOINT + "/{extId}", accExtId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Account.class);
    }
}
