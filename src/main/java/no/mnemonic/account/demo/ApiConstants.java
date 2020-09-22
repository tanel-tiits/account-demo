package no.mnemonic.account.demo;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String API_VERSION = "v1";

    public static final String API_BASE_URL = "/api/" + API_VERSION;

    public static final String ROOT_CONTROLLER_ENDPOINT = API_BASE_URL + "/";
    public static final String ACCOUNT_CONTROLLER_ENDPOINT = API_BASE_URL + "/account";
    public static final String TRANSACTION_CONTROLLER_ENDPOINT = API_BASE_URL + "/transaction";

    public static final String TAG_ACCOUNT = "Account " + API_VERSION;
    public static final String TAG_TRANSACTION = "Transaction " + API_VERSION;

    public static final String HTTP_RESPONSE_CODE_OK = "200";
    public static final String HTTP_RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String HTTP_RESPONSE_CODE_NOT_FOUND = "404";
    public static final String HTTP_RESPONSE_CODE_INT_SRV_ERROR = "500";
}
