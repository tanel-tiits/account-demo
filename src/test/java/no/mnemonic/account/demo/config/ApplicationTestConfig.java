package no.mnemonic.account.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(ApplicationConfig.class)
public class ApplicationTestConfig {}
