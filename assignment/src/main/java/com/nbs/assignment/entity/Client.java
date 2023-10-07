package com.nbs.assignment.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Client class is used to define client's information.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    /**
     * firstName
     */
    @NotBlank
    private String firstName;

    /**
     * lastName
     */
    @NotBlank
    private String lastName;

    /**
     * mobileNumber
     */
    private String mobileNumber;

    /**
     * idNumber
     */
    @NotBlank
    private String idNumber;

    /**
     * physicalAddress
     */
    private String physicalAddress;

}

