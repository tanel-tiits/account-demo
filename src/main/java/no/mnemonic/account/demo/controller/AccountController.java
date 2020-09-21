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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.mnemonic.account.demo.ApiConstants;
import no.mnemonic.account.demo.exception.NotFoundException;
import no.mnemonic.account.demo.model.Account;
import no.mnemonic.account.demo.model.ErrorData;
import no.mnemonic.account.demo.service.AccountService;

@RestController
@RequestMapping(path = ApiConstants.ACCOUNT_CONTROLLER_ENDPOINT)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(tags = {ApiConstants.API_VERSION}, operationId = "getAllAccounts")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_OK, description = "Retrieves all accounts")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_INT_SRV_ERROR, description = "Something fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/{extId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(tags = {ApiConstants.API_VERSION}, operationId = "getAccountById")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_OK, description = "Retrieves an account by Id")
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_NOT_FOUND, description = "The account is not found",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    @ApiResponse(responseCode = ApiConstants.HTTP_RESPONSE_CODE_INT_SRV_ERROR, description = "Something else fails",
        content = @Content(schema = @Schema(implementation = ErrorData.class)))
    public ResponseEntity<Account> getById(@PathVariable(name = "extId", required = true) String extId) {

        Optional<Account> account = accountService.findByExternalId(extId);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Account not found! (accExtId=" + extId + ")");
    }
}
