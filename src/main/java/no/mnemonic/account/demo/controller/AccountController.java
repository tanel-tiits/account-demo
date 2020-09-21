package no.mnemonic.account.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.mnemonic.account.demo.exception.NotFoundException;
import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.service.AccountService;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Account> getById(@PathVariable(name = "extId", required = true) String extId) {

        Optional<Account> account = accountService.findByExternalId(extId);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Account not found! (accExtId=" + extId + ")");
    }
}
