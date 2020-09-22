package no.mnemonic.account.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import no.mnemonic.account.demo.ApiConstants;

@RestController
@OpenAPIDefinition(info = @Info(
        title = "Account and Transaction API",
        version = ApiConstants.API_VERSION,
        contact = @Contact(email = "tanel.tiits@gmail.com"),
        description = "A small toy API for handling transactions across accounts"))
@RequestMapping(path = ApiConstants.ROOT_CONTROLLER_ENDPOINT)
public class RootController {}
