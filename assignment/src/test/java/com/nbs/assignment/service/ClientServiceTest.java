package com.nbs.assignment.service;

import com.nbs.assignment.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * The ClientServiceTest class is responsible for testing the ClientService class.
 * It contains test cases for adding, getting, updating, and searching clients.
 */

public class ClientServiceTest {

    private ClientService clientService;
    private List<Client> clients;

    @BeforeEach
    void setUp() {
        clients = new ArrayList<>();
        clientService = new ClientService(clients);
    }

    @Test
    void createClientTest() {
        Client client = new Client("Manas", "Bhawale", "9595258305", "1401901212190", "India");

        Client newClient = clientService.addClient(client);

        assertNotNull(newClient);
        assertEquals(client, newClient);
        assertEquals(client.getFirstName(), newClient.getFirstName());
    }

    @Test
    void getClientByIdTest() {

        Client existingClient = new Client("Manas", "Bhawale", "9595258305", "1401901212190", "India");
        clients.add(existingClient);

        Client client = clientService.getClientById(existingClient.getIdNumber());

        assertNotNull(client);
        assertEquals(existingClient, client);
    }

    @Test
    void updateClientTest() {

        Client existingClient = new Client("Manas", "Bhawale", "9595258305", "1401901212190", "India");
        clients.add(existingClient);

        String updatedFirstName = "Mahi";
        String updatedLastName = "Bhawale";
        String updatedMobileNumber = "9595258306";
        String updatedIdNumber = "1401901212191";
        String updatedAddress = "India";
        Client toUpdate = new Client(updatedFirstName, updatedLastName, updatedMobileNumber, updatedIdNumber, updatedAddress);

        Client updatedClient = clientService.updateClient(existingClient.getIdNumber(), toUpdate);

        assertNotNull(updatedClient);
        assertEquals(updatedFirstName, updatedClient.getFirstName());

    }

    @Test
    void searchClientTest() {

        Client client = new Client("Manas", "Bhawale", "9595258305", "1401901212190", "India");
        clients.add(client);
        String firstName = "Manas";

        List<Client> result = clientService.findClient(firstName, null, null);

        assertTrue(!CollectionUtils.isEmpty(result));
        assertEquals(firstName, result.get(0).getFirstName());
    }
}








