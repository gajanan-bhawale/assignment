package com.nbs.assignment.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * The ErrorResponseModel class is used to represent the error response in
 * case of an exception or error occurring in the application
 */

@Setter
@Getter
public class ErrorResponseModel {

    public ErrorResponseModel(String timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponseModel() {
    }

    /**
     * timestamp
     */

    private String timestamp;

    /**
     * status
     */

    private int status;

    /**
     * error
     */

    private String error;

    /**
     * message
     */

    private String message;

}
