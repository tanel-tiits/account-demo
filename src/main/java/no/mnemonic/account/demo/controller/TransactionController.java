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

import no.mnemonic.account.demo.exception.NotFoundException;
import no.mnemonic.account.demo.model.Transaction;
import no.mnemonic.account.demo.service.TransactionService;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Transaction> getById(@PathVariable(name = "extId", required = true) String extId) {

        Optional<Transaction> transaction = transactionService.findByExternalId(extId);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Transaction not found! (txExtId=" + extId + ")");
    }

    @PutMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> save(@PathVariable(name = "extId", required = true) String extId,
            @RequestBody(required = true) Transaction transaction) {

        transactionService.saveTransaction(extId, transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
