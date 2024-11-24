package com.business.customer.service;

import java.util.List;
import java.util.UUID;

import com.business.customer.entity.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	
	public Customer getCustomerById(UUID uuid);
	
	public List<Customer> getAllCustomer();
	
	public Customer updateCustomerById(UUID uuid,Customer customer);
	
	public boolean deleteCustomerById(UUID uuid);
	
	public void deleteAllCustomer();

}
