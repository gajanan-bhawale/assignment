package com.nbs.assignment.exception;
import com.nbs.assignment.constants.Constant;
import com.nbs.assignment.entity.BaseResponse;
import com.nbs.assignment.entity.Client;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    /**
     * Exception handler for IllegalArgumentException.
     * Handles validation exceptions and returns an ErrorResponse with the corresponding error details.
     *
     * @param ex The IllegalArgumentException instance.
     * @return The ResponseEntity containing the ErrorResponse and HttpStatus.BAD_REQUEST.
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage().equals(Constant.INVALID_ID_NUMBER)) {
            ErrorResponseModel errorResponse = new ErrorResponseModel(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
            BaseResponse<ErrorResponseModel> baseResponse = new BaseResponse<>(errorResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
        else if (ex.getMessage().equals(Constant.CLIENT_NOT_FOUND)) {
            ErrorResponseModel errorResponse = new ErrorResponseModel(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
            BaseResponse<ErrorResponseModel> baseResponse = new BaseResponse<>(errorResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } else {
            ErrorResponseModel errorResponse = new ErrorResponseModel(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage());
            BaseResponse<ErrorResponseModel> baseResponse = new BaseResponse<>(errorResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
        }
    }


    /**
     * Exception handler for ConstraintViolationException.
     * Handles constraint violation exceptions and returns an ErrorResponse with the corresponding error details.
     *
     * @param ex The ConstraintViolationException instance.
     * @return The ResponseEntity containing the ErrorResponse and HttpStatus.BAD_REQUEST.
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage()).append("; ");
        }
        ErrorResponseModel errorResponse = new ErrorResponseModel(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage.toString().trim());
        BaseResponse<ErrorResponseModel> baseResponse = new BaseResponse<>(errorResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

}
