package com.business.customer.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.customer.entity.Customer;
import com.business.customer.repository.CustomerRepository;
import com.business.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Customer createCustomer(Customer customer) {
		logger.info("Entering createCustomer method for customer: {}", customer.getCustomerFirstName());
		return customerRepository.createCustomer(customer);
	}

	@Override
	public Customer getCustomerById(UUID uuid) {
		logger.info("Attempting to fetch customer by ID: {}", uuid);
		return customerRepository.getCustomerByCustomerId(uuid);
	}

	@Override
	public List<Customer> getAllCustomer() {
		logger.info("Fetching all customers from the database.");
		return customerRepository.getAllCustomers();
	}

	@Override
	public Customer updateCustomerById(UUID uuid, Customer customer) {
		logger.info("Attempting to update customer with ID: {}", uuid);
		return customerRepository.updateCustomer(uuid, customer);
	}

	@Override
	public boolean deleteCustomerById(UUID uuid) {
		logger.info("Attempting to delete customer with ID: {}", uuid);
		return customerRepository.deleteCustomerByCustomerId(uuid)>0;
	}

	@Override
	public void deleteAllCustomer() {
		logger.info("Attempting to delete all customers from the database.");
		customerRepository.deleteAllCustomers();
	}

}
