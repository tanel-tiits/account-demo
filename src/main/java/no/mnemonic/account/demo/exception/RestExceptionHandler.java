package no.mnemonic.account.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import no.mnemonic.account.demo.model.ErrorData;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<ErrorData> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(createErrorData(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<ErrorData> handleInvalidTransactionException(InvalidTransactionException e,
            HttpServletRequest request) {
        return new ResponseEntity<>(createErrorData(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<ErrorData> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
            HttpServletRequest request) {

        return new ResponseEntity<>(new ErrorData(HttpMessageNotReadableException.class.getSimpleName(),
                "Required request body is missing or invalid!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<ErrorData> handleGenericException(Exception e, HttpServletRequest request) {

        logger.error("Following exception bubbled up to REST-endpoint", e);
        return new ResponseEntity<>(createErrorData(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ErrorData createErrorData(Throwable t) {

        return new  ErrorData(t.getClass().getSimpleName(), t.getMessage()
                + (t.getCause() != null ? " (" + t.getCause().getMessage() + ")" : ""));
    }
}
