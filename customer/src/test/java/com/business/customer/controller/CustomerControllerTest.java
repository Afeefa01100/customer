package com.business.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.business.customer.dto.CustomerDTO;
import com.business.customer.entity.Customer;
import com.business.customer.exception.CustomerNotFoundException;
import com.business.customer.repository.CustomerRepository;
import com.business.customer.serviceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
    private JdbcTemplate jdbcTemplate;
	
	@Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerServiceImpl customerServiceImpl;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer(customerId, "Ram", "M", "Doe", "Ram.doe@example.com", "1234567890");
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testWelcome() throws Exception {
        mockMvc.perform(get("/customers/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am good ,please come ")); 
    }

    @Test
    void testGetCustomerById_Success() throws Exception {
        when(customerServiceImpl.getCustomerById(customerId)).thenReturn(customer);

        mockMvc.perform(get("/customers/{uuid}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId.toString()))
                .andExpect(jsonPath("$.customerFirstName").value("Ram"))
                .andExpect(jsonPath("$.customerLastName").value("Doe"));

        verify(customerServiceImpl, times(1)).getCustomerById(customerId);
    }

    @Test
    void testGetCustomerById_NotFound() throws Exception {
        when(customerServiceImpl.getCustomerById(customerId)).thenReturn(null);

        mockMvc.perform(get("/customers/{uuid}", customerId))
                .andExpect(status().isNotFound());

        verify(customerServiceImpl, times(1)).getCustomerById(customerId);
    }

//    @Test
//    void testCreateCustomer_Success() throws Exception {
//    	// Create a mock customer object
//        Customer customer =new Customer (customerId, "Ram", "M", "Doe", "Ram.doe@example.com", "1234567890");
//
//        // Mock the service call to return the created customer
//        when(customerServiceImpl.createCustomer(any(Customer.class))).thenReturn(customer);
//
//        // Perform the POST request to create the customer
//        mockMvc.perform(post("/customers/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"customerFirstName\":\"Ram\", \"customerLastName\":\"Doe\", \"customerEmail\":\"Ram.doe@example.com\", \"customerPhoneNumber\":\"1234567890\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.customerFirstName").value("Ram"))  // Check if the customerFirstName is correct in the response
//                .andExpect(jsonPath("$.customerLastName").value("Doe"));   // Check if the customerLastName is correct in the response
//    }

    @Test
    void testCreateCustomer_BadRequest() throws Exception {

        Customer invalidCustomer = new Customer();
        invalidCustomer.setCustomerFirstName(null); 
        invalidCustomer.setCustomerLastName(null);
        invalidCustomer.setCustomerEmail(null); 

        when(customerServiceImpl.createCustomer(any(Customer.class)))
                .thenThrow(new IllegalArgumentException("Invalid customer data"));

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidCustomer))) 
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid customer data"));

        verify(customerServiceImpl, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_Success() throws Exception {
    	CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUuid(customerId);
        customerDTO.setCustomer(customer);
        when(customerServiceImpl.updateCustomerById(any(UUID.class), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"uuid\": \"" + customerId + "\", \"customer\": {\"customerFirstName\": \"Ram\", \"customerMiddleName\": \"M\", \"customerLastName\": \"Doe\", \"customerEmail\": \"Ram.doe@example.com\", \"customerPhoneNumber\": \"1234567890\"}}"))
                    .andExpect(status().isOk()) 
                    .andExpect(jsonPath("$.customerFirstName").value("Ram")) 
                    .andExpect(jsonPath("$.customerLastName").value("Doe"));  

        verify(customerServiceImpl, times(1)).updateCustomerById(any(UUID.class), any(Customer.class));
    }

    @Test
    void testUpdateCustomer_NotFound() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUuid(customerId);
        customerDTO.setCustomer(customer);

        when(customerServiceImpl.updateCustomerById(any(UUID.class), any(Customer.class))).thenReturn(null);

        mockMvc.perform(put("/customers/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"uuid\": \"" + customerId + "\", \"customer\": {\"customerFirstName\": \"Ram\", \"customerMiddleName\": \"M\", \"customerLastName\": \"Doe\", \"customerEmail\": \"Ram.doe@example.com\", \"customerPhoneNumber\": \"1234567890\"}}"))
                    .andExpect(status().isNotFound()); 

        verify(customerServiceImpl, times(1)).updateCustomerById(any(UUID.class), any(Customer.class));
    }

    @Test
    void testDeleteCustomerById_Success() throws Exception {
    	when(customerServiceImpl.deleteCustomerById(customerId)).thenReturn(true);

        mockMvc.perform(delete("/customers/{uuid}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Customer ID : " + customerId));

        verify(customerServiceImpl, times(1)).deleteCustomerById(customerId);
    }

    @Test
    void testDeleteCustomerById_NotFound() throws Exception {

    	doThrow(new CustomerNotFoundException("Customer not found with ID: " + customerId))
        .when(customerServiceImpl).deleteCustomerById(customerId);

    	mockMvc.perform(delete("/customers/{uuid}", customerId))
        .andExpect(status().isNotFound()) 
        .andExpect(content().string("Customer not found with ID: " + customerId));

    	verify(customerServiceImpl, times(1)).deleteCustomerById(customerId);
    }

    @Test
    void testDeleteAllCustomers() throws Exception {
        doNothing().when(customerServiceImpl).deleteAllCustomer();

        mockMvc.perform(delete("/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().string("All Customers are Deleted"));

        verify(customerServiceImpl, times(1)).deleteAllCustomer();
    }

    @Test
    void testGetAllCustomers() throws Exception {

        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer(customerId, "Ram", "M", "Sharma", "Ram.shar@example.com", "1234567890"));
        mockCustomers.add(new Customer(customerId, "Sayam", "M", "Yadav", "Ram.yad@example.com", "1234567890"));


        when(customerServiceImpl.getAllCustomer()).thenReturn(mockCustomers);

        mockMvc.perform(get("/customers/"))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.length()").value(2)) 
                .andExpect(jsonPath("$[0].customerFirstName").value("Ram"))
                .andExpect(jsonPath("$[1].customerFirstName").value("Sayam"));

        verify(customerServiceImpl, times(1)).getAllCustomer();
    }

}
