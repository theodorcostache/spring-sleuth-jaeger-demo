package de.costache.tracing.error;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiErrorController extends ResponseEntityExceptionHandler {

    @Autowired
    private Tracer tracer;

    private final Logger logger = LoggerFactory.getLogger(ApiErrorController.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleAll(Exception ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        String.format("Internal Server Error (traceId: %s)",
                                tracer.currentSpan().context().traceIdString()), ex.getClass().getCanonicalName(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}