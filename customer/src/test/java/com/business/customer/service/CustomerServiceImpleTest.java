package com.business.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.business.customer.entity.Customer;
import com.business.customer.repository.CustomerRepository;
import com.business.customer.serviceImpl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImpleTest {
	
	@InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        customer = new Customer(customerId, "Ram", "Kumar","Yadav","rk.Yadav7794@gmail.com","123456789");
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.createCustomer(any(Customer.class))).thenReturn(customer);
        Customer result = customerService.createCustomer(customer);
        
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Ram", result.getCustomerFirstName());
        assertEquals("Kumar", result.getCustomerMiddleName());
        assertEquals("Yadav", result.getCustomerLastName());
        assertEquals("rk.Yadav7794@gmail.com", result.getCustomerEmail());
        assertEquals("123456789", result.getCustomerPhoneNumber());
        verify(customerRepository, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.getCustomerByCustomerId(customerId)).thenReturn(customer);
        Customer result = customerService.getCustomerById(customerId);


        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Ram", result.getCustomerFirstName());
        assertEquals("Kumar", result.getCustomerMiddleName());
        assertEquals("Yadav", result.getCustomerLastName());
        assertEquals("rk.Yadav7794@gmail.com", result.getCustomerEmail());
        assertEquals("123456789", result.getCustomerPhoneNumber());
        verify(customerRepository, times(1)).getCustomerByCustomerId(customerId);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
            new Customer(UUID.randomUUID(), "Ram", "Kumar","Yadav","rk.Yadav7794@gmail.com","123456789"),
            new Customer(UUID.randomUUID(), "Prem", "Kumar","Yadav","pk.Yadav7794@gmail.com","123456789")
        );
        when(customerRepository.getAllCustomers()).thenReturn(customers);
        List<Customer> result = customerService.getAllCustomer();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).getAllCustomers();
    }

    @Test
    void testUpdateCustomerById() {
        Customer updatedCustomer = new Customer(customerId, "Kaju", "Kumar","Yadav","kk.Yadav7794@gmail.com","123456789");
        when(customerRepository.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomerById(customerId, updatedCustomer);
        assertNotNull(result);
        assertEquals("Kaju", result.getCustomerFirstName());
        assertEquals("kk.Yadav7794@gmail.com", result.getCustomerEmail());
        verify(customerRepository, times(1)).updateCustomer(customerId, updatedCustomer);
    }

    @Test
    void testDeleteCustomerById() {
        customerService.deleteCustomerById(customerId);
        verify(customerRepository, times(1)).deleteCustomerByCustomerId(customerId);
    }

    @Test
    void testDeleteAllCustomers() {
        customerService.deleteAllCustomer();
        verify(customerRepository, times(1)).deleteAllCustomers();
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.getCustomerByCustomerId(customerId)).thenReturn(null);
        Customer result = customerService.getCustomerById(customerId);
        assertNull(result);
        verify(customerRepository, times(1)).getCustomerByCustomerId(customerId);
    }

}
