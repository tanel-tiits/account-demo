package no.mnemonic.account.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.mnemonic.account.demo.ApiConstants;
import no.mnemonic.account.demo.exception.NotFoundException;
import no.mnemonic.account.demo.model.ErrorData;
import no.mnemonic.account.demo.model.Transaction;
import no.mnemonic.account.demo.service.TransactionService;

@RestController
@RequestMapping(path = ApiConstants.TRANSACTION_CONTROLLER_ENDPOINT)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(tags = {ApiConstants.TAG_TRANSACTION}, operationId = "getAllTransaction")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_OK, description = "Retrieves all transactions")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_INT_SRV_ERROR, description = "Something fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(tags = {ApiConstants.TAG_TRANSACTION}, operationId = "getTransactionById")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_OK, description = "Retrieves a transactions by Id")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_NOT_FOUND, description = "The transactions is not found",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_INT_SRV_ERROR, description = "Something else fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    public ResponseEntity<Transaction> getById(@PathVariable(name = "extId", required = true) String extId) {

        Optional<Transaction> transaction = transactionService.findByExternalId(extId);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Transaction not found! (txExtId=" + extId + ")");
    }

    @PutMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(tags = {ApiConstants.TAG_TRANSACTION}, operationId = "saveTransaction")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_OK, description = "Stores a transaction")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_BAD_REQUEST, description = "Transaction validation fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_INT_SRV_ERROR, description = "Something else fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    public ResponseEntity<Transaction> save(@PathVariable(name = "extId", required = true) String extId,
            @RequestBody(required = true) Transaction transaction) {

        Transaction savedTx = transactionService.saveTransaction(extId, transaction);
        return new ResponseEntity<>(savedTx, HttpStatus.OK);
    }
}
