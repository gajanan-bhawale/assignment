package com.nbs.assignment.resource;

import com.nbs.assignment.entity.BaseResponse;
import com.nbs.assignment.entity.Client;
import com.nbs.assignment.service.ClientService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ClientResource class is responsible for handling client-related API endpoints.
 * It provides operations to add, get, update and search clients.
 */
@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;


    /**
     * This method is used to create a client.
     *
     * @param client The client object to be created.
     * @return The ResponseEntity containing the created client.
     */

    @PostMapping
    public ResponseEntity<BaseResponse> createClient(@Valid @RequestBody Client client) {
        Client createdClient = clientService.addClient(client);
        log.info("---- New Client added ----");
        BaseResponse<Client> baseResponse = new BaseResponse<>(createdClient);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    /**
     * This method is used to get a client by ID.
     *
     * @param idNumber The ID number of the client.
     * @return The ResponseEntity containing the client.
     */

    @GetMapping("/{idNumber}")
    public ResponseEntity<BaseResponse> getClientById(@PathVariable String idNumber) {
        Client client = clientService.getClientById(idNumber);
        if (client != null) {
            BaseResponse<Client> baseResponse = new BaseResponse<>(client);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This method is used to update a client.
     *
     * @param idNumber The ID number of the client to update.
     * @param client   The updated client object.
     * @return The ResponseEntity containing the updated client.
     */

    @PutMapping("/{idNumber}")
    public ResponseEntity<BaseResponse> updateClient(@PathVariable String idNumber, @Valid @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(idNumber, client);
        if (updatedClient != null) {
            log.info("---- Client updated ----", updatedClient.getIdNumber());
            BaseResponse<Client> baseResponse = new BaseResponse<>(updatedClient);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This method is used to search for clients.
     *
     * @param firstName    The first name of the client.
     * @param idNumber     The ID number of the client.
     * @param mobileNumber The mobile number of the client.
     * @return The ResponseEntity containing the search result.
     */

    @GetMapping("/search")
    public ResponseEntity<BaseResponse> searchClient(@RequestParam(required = false) String firstName,
                                                     @RequestParam(required = false) String idNumber,
                                                     @RequestParam(required = false) String mobileNumber) {

        if ( StringUtils.isBlank(firstName) && StringUtils.isBlank(idNumber) && StringUtils.isBlank(mobileNumber)) {
            log.warn("---- First Name or ID Number or Mobile Number should not be null ----");
            return ResponseEntity.badRequest().build();
        }

        List<Client> clients = clientService.findClient(firstName, idNumber, mobileNumber);
        if (!CollectionUtils.isEmpty(clients)) {
            BaseResponse<List<Client>> baseResponse = new BaseResponse<>(clients);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
