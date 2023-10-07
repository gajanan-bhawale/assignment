package com.nbs.assignment.service;

import com.nbs.assignment.constants.Constant;
import com.nbs.assignment.entity.Client;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * The ClientService class handles the business logic for managing clients.
 * It provides methods for adding, getting, updating, and searching clients.
 */

@Service
public class ClientService {

    private List<Client> clients;

    public ClientService() {
        this.clients = new ArrayList<>();
    }

    public ClientService(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Add a new client.
     *
     * @param client The client object to be created.
     * @return The created client.
     * @throws IllegalArgumentException if the ID Number or mobile number is invalid or already exists.
     */
    @Transactional
    public Client addClient(Client client) {
        validations(client);
        clients.add(client);
        return client;
    }

    /**
     *  This method is used for client validations
     * @param client
     */
    private void validations(Client client){
        if (!isSouthAfricanIdNumberValid(client.getIdNumber())) {
            throw new IllegalArgumentException(Constant.INVALID_ID_NUMBER);
        }else if (isDuplicateIdNumber(client.getIdNumber())) {
            throw new IllegalArgumentException(Constant.DUPLICATE_ID_NUMBER);
        }else if (StringUtils.isNotBlank(client.getMobileNumber()) && isDuplicateMobileNumber(client.getMobileNumber())) {
            throw new IllegalArgumentException(Constant.DUPLICATE_MOBILE_NUMBER);
        }
    }
    /**
     * get a client by id.
     *
     * @param idNumber The ID number of the client.
     * @return The client with the specified ID, or null if not found.
     */
    @Transactional
    public Client getClientById(String idNumber) {
        return clients.stream()
                .filter(client -> client.getIdNumber().equals(idNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Constant.CLIENT_NOT_FOUND));
    }

    /**
     * To update a client.
     *
     * @param idNumber      The ID number of the client to update.
     * @param newClient The updated client object.
     * @return The updated client.
     * @throws IllegalArgumentException if the ID number or mobile number is invalid or already exists,
     *                                  or if the client with the specified ID is not found.
     */
    @Transactional
    public Client updateClient(String idNumber, Client newClient) {
        Client client = getClientById(idNumber);
        if (client != null) {

            if (!isSouthAfricanIdNumberValid(newClient.getIdNumber())) {
                throw new IllegalArgumentException(Constant.INVALID_ID_NUMBER);
            }else if (client.getIdNumber().equals(newClient.getIdNumber()) && isDuplicateIdNumber(newClient.getIdNumber())) {
                throw new IllegalArgumentException(Constant.DUPLICATE_ID_NUMBER);
            }else if (client.getMobileNumber().equals(newClient.getMobileNumber()) && isDuplicateMobileNumber(newClient.getMobileNumber())) {
                throw new IllegalArgumentException(Constant.DUPLICATE_MOBILE_NUMBER);
            }

            client.setFirstName(newClient.getFirstName());
            client.setLastName(newClient.getLastName());
            client.setMobileNumber(newClient.getMobileNumber());
            client.setIdNumber(newClient.getIdNumber());
            client.setPhysicalAddress(newClient.getPhysicalAddress());

            return client;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     Find clients based on the firstName, idNumber, mobileNumber.
     @param firstName The first name of the client.
     @param idNumber The ID number of the client.
     @param mobileNumber The mobile number of the client.
     @return The client matching the search criteria.
     @throws IllegalArgumentException If no search criteria are provided or if a client with the specified first name, ID number, or mobile number is not found.
     */

    public List<Client> findClient(String firstName, String idNumber, String mobileNumber) {
        if (StringUtils.isNotBlank(firstName)) {
            return clients.stream()
                    .filter(client -> client.getFirstName().toLowerCase().startsWith(firstName.toLowerCase())).toList();
        }else if (StringUtils.isNotBlank(mobileNumber)) {
            return clients.stream()
                    .filter(client -> client.getMobileNumber().toLowerCase().startsWith(mobileNumber.toLowerCase())).toList();

        }else if (StringUtils.isNotBlank(idNumber )) {
            return clients.stream()
                    .filter(client -> client.getIdNumber().toLowerCase().startsWith(idNumber.toLowerCase())).toList();
        }

        return Collections.emptyList();
    }


    /**
     * Checks if a South African ID number is valid.
     *
     * @param idNumber The ID number to validate.
     * @return true if the ID number is valid, false otherwise.
     */
    private boolean isSouthAfricanIdNumberValid(String idNumber) {
        if (idNumber.length() != 13 || !idNumber.matches("\\d{13}")) {
            return false;
        }
        char _12Digit = idNumber.charAt(11);

        if (_12Digit != '8' && _12Digit != '9') {
            return false;
        }
        return true;
    }

    /**
     * Checks if an ID number already exists for any client.
     *
     * @param idNumber The ID number to check.
     * @return true if the ID number is a duplicate, false otherwise.
     */
    private boolean isDuplicateIdNumber(String idNumber) {
        return clients.stream()
                .anyMatch(client -> client.getIdNumber().equals(idNumber));
    }

    /**
     * Checks if a mobile number already exists for any client.
     *
     * @param mobileNumber The mobile number to check.
     * @return true if the mobile number is a duplicate, false otherwise.
     */
    private boolean isDuplicateMobileNumber(String mobileNumber) {
        return clients.stream()
                .anyMatch(client -> client.getMobileNumber().equals(mobileNumber));
    }

}
