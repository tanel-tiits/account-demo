package no.mnemonic.account.demo;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String API_VERSION = "v1";

    public static final String API_BASE_URL = "/api/" + API_VERSION;

    public static final String ACCOUNT_CONTROLLER_ENDPOINT = API_BASE_URL + "/account";

    public static final String TRANSACTION_CONTROLLER_ENDPOINT = API_BASE_URL + "/transaction";
}
