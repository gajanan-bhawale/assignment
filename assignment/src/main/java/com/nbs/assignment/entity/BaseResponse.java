package com.nbs.assignment.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {

    /*** represents actual response. */
    protected T result;

    /**
     * Initializes BaseResponse with response.
     *
     * @param result result to be returned.
     */
    public BaseResponse(T result) {
        this.result = result;
    }

}
