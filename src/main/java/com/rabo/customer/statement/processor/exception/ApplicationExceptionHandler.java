package com.rabo.customer.statement.processor.exception;

import com.rabo.customer.statement.processor.common.CommonConstants;
import com.rabo.customer.statement.processor.controller.CustomerStatementController;
import com.rabo.customer.statement.processor.model.Results;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This a exception controller advice class which handles the exception
 *
 * @author Ragesh Sharma
 */
@Slf4j
@RestControllerAdvice(assignableTypes = CustomerStatementController.class)
public class ApplicationExceptionHandler {


    /**
     * For ApplicationRuntimeException
     *
     * @param ex exception
     * @return ExceptionResponse Object
     */
    @ExceptionHandler(ApplicationRuntimeException.class)
    public ResponseEntity<Results> handleApplicationException(ApplicationRuntimeException ex) {
        log.error("Application Runtime Exception {}", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(formResults(CommonConstants.INTERNAL_SERVER_ERROR));
    }

    /**
     * For ConstraintViolationException
     *
     * @param ex exception
     * @return ExceptionResponse Object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Results> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Invalid Input {}", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formResults(CommonConstants.BAD_REQUEST));
    }


    /**
     * For HttpMessageNotReadableException
     *
     * @param ex exception
     * @return ExceptionResponse Object
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Results> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid Input {}", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formResults(CommonConstants.BAD_REQUEST));
    }

    private Results formResults(String result) {
        Results results = new Results();
        results.setResult(result);
        return results;
    }

}
