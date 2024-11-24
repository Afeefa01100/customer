package com.business.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.business.customer.entity.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {
	
	@Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CustomerRepository customerRepository;

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
        
        customerId = UUID.randomUUID();
        customer = new Customer(customerId, "Ram", "A", "Yadav", "Ram.Yadav@example.com", "1234567890");
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> mockedCustomers = new ArrayList<Customer>();
        mockedCustomers.add(new Customer());
        when(jdbcTemplate.query(eq("Select * from Customer"), any(RowMapper.class))).thenReturn(mockedCustomers);
        List<Customer> result = customerRepository.getAllCustomers();
        assertEquals(mockedCustomers, result);
    }

    @Test
    void testGetCustomerByCustomerId_Found() {
    	when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
        .thenReturn(List.of(customer));
        Customer result = customerRepository.getCustomerByCustomerId(customerId);
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Ram", result.getCustomerFirstName());
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    }

    @Test
    void testGetCustomerByCustomerId_NotFound() {
    	when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
        .thenReturn(List.of()); 
        Customer result = customerRepository.getCustomerByCustomerId(customerId);

        assertNull(result);
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    }

    @Test
    void testCreateCustomer() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);
        Customer result = customerRepository.createCustomer(customer);

        assertNotNull(result);
        assertEquals(customer.getCustomerFirstName(), result.getCustomerFirstName());
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testCreateCustomer_Failure() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any()))
                .thenReturn(0); 

        Customer result = customerRepository.createCustomer(customer);
        assertNull(result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeleteCustomerByCustomerId() {
    	when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(0);
    	
        int result = customerRepository.deleteCustomerByCustomerId(customerId);

        assertEquals(0, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class)); 
    }

    @Test
    void testDeleteCustomerByCustomerId_Failure() {
    	when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(0);

        int result = customerRepository.deleteCustomerByCustomerId(customerId);

        assertEquals(0, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class)); 
    }

    @Test
    void testDeleteAllCustomers() {
        when(jdbcTemplate.update(anyString())).thenReturn(1);

        int result = customerRepository.deleteAllCustomers();

        assertEquals(1, result); 
        verify(jdbcTemplate, times(1)).update(anyString());
    }

    @Test
    void testDeleteAllCustomers_Failure() {
        when(jdbcTemplate.update(anyString())).thenReturn(0);

        int result = customerRepository.deleteAllCustomers();

        assertEquals(0, result); 
        verify(jdbcTemplate, times(1)).update(anyString());
    }

    @Test
    void testUpdateCustomer() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        Customer result = customerRepository.updateCustomer(customerId, customer);

        assertNotNull(result);
        assertEquals(customer.getCustomerFirstName(), result.getCustomerFirstName());
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testUpdateCustomer_Failure() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any())).thenReturn(0); 
        Customer result = customerRepository.updateCustomer(customerId, customer);
        assertNull(result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any());
    }

}
