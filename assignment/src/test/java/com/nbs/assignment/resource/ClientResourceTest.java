package com.nbs.assignment.resource;

import com.nbs.assignment.entity.BaseResponse;
import com.nbs.assignment.entity.Client;
import com.nbs.assignment.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


/**
 *
 * The ClientResourceTest class is responsible for testing the ClientController class.
 * It contains test cases for creating, retrieving, updating, and searching clients.
 */
public class ClientResourceTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientResource clientResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClientTest() {

        Client client = new Client("Gajanan", "Bhawale", "9595258305", "1401905009991", "India");
        Client createdClient = new Client("Gajanan", "Bhawale", "9595258305", "1401905009991", "India");
        when(clientService.addClient(client)).thenReturn(createdClient);

        ResponseEntity<BaseResponse> response = clientResource.createClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody().getResult());
        verify(clientService, times(1)).addClient(client);
    }

    @Test
    void getClientByIdTest() {

        String idNumber = "1401905009991";
        Client client = new Client("Gajanan", "Bhawale", "9595258306", "1401905009991", "India");
        when(clientService.getClientById(idNumber)).thenReturn(client);

        ResponseEntity<BaseResponse> response = clientResource.getClientById(idNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody().getResult());
        verify(clientService, times(1)).getClientById(idNumber);
    }

    @Test
    void searchClientTest() {

        String firstName = "Gajanan";
        String idNumber = "1401905009991";
        String mobileNumber = "9595258305";
        Client client = new Client(firstName, "Bhawale", mobileNumber, idNumber, "India");
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        when(clientService.findClient(firstName, idNumber, mobileNumber)).thenReturn(clients);
        ResponseEntity<BaseResponse> response = clientResource.searchClient(firstName, idNumber, mobileNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody().getResult());
        verify(clientService, times(1)).findClient(firstName, idNumber, mobileNumber);
    }

}
