package no.roseweb.workplanner.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);
    private static final String KORRELASJONS_ID = "Korrelasjonsid";
    private static final String FEIL_ID = "Feilid";

    @ExceptionHandler({ IllegalArgumentException.class })
    protected ResponseEntity<Object> handleBadRequest(IllegalArgumentException e, WebRequest request) {
        return handleException(e, request, HttpStatus.BAD_REQUEST, true);
    }

    @ExceptionHandler({ HttpServerErrorException.class })
    protected ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException e,
        WebRequest request) {
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, true);
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    protected ResponseEntity<Object> handleHttpClientException(HttpClientErrorException e,
        WebRequest request) {
        if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
            return handleException(e, request, HttpStatus.NOT_FOUND, true);
        }
        if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode())) {
            return handleException(e, request, HttpStatus.BAD_REQUEST, true);
        }
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, true);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    protected ResponseEntity<Object> handleAccessDenied(RuntimeException e, WebRequest request) {
        return handleException(e, request, HttpStatus.FORBIDDEN, false);
    }

    @ExceptionHandler({ RuntimeException.class })
    protected ResponseEntity<Object> handleGenericError(RuntimeException e, WebRequest request) {
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, true);
    }

    private ResponseEntity<Object> handleException(final RuntimeException e, WebRequest request,
        HttpStatus httpStatus, boolean warning) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String feilId = UUID.randomUUID().toString();
        if (warning) {
            LOG.warn("Exception Occured. Feilid={} Sending status={} Message={}",
                feilId, httpStatus.value(), e.getMessage());
        } else {
            LOG.info("Exception Occured. Feilid={} Sending status={} Message={}",
                feilId, httpStatus.value(), e.getMessage());
        }

        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", e.getMessage());
        error.put(FEIL_ID, feilId);
        error.put(KORRELASJONS_ID, MDC.get(KORRELASJONS_ID));
        if (e.getCause() != null) {
            error.put("cause", e.getCause().getMessage());
        }
        return handleExceptionInternal(e, error, headers, httpStatus, request);
    }
}
