package com.business.customer.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business.customer.dto.CustomerDTO;
import com.business.customer.entity.Customer;
import com.business.customer.exception.CustomerNotFoundException;
import com.business.customer.serviceImpl.CustomerServiceImpl;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@GetMapping("/welcome")
	public ResponseEntity<String> welcome()
	{
		logger.info("Welcome endpoint accessed.");
		return new ResponseEntity<String>("I am good ,please come ",HttpStatus.OK);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable UUID uuid)
	{
		logger.info("Fetching customer with ID: {}", uuid);
		Customer customer=customerServiceImpl.getCustomerById(uuid);
		
		if (customer == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer)
	{
		logger.info("Creating customer: {} {}", customer.getCustomerFirstName(), customer.getCustomerLastName());
		
		
		try
		{
			Customer tempCustomer=customerServiceImpl.createCustomer(customer);
			return new ResponseEntity<String>("Customer created successfully",HttpStatus.CREATED);
		}
		catch(IllegalArgumentException ex) 
		{
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Customer>> getAllCustomer()
	{
		logger.info("Fetching all customers.");
		List<Customer> lCustomers=customerServiceImpl.getAllCustomer();
		return new ResponseEntity<List<Customer>>(lCustomers,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Customer> updateCustomerByCustomerId(@RequestBody CustomerDTO customerDTO)
	{
		logger.info("Updating customer with ID: {}", customerDTO.getUuid());
		Customer customer=customerServiceImpl.updateCustomerById(customerDTO.getUuid(), customerDTO.getCustomer());
		
		if (customer == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable UUID uuid)
	{
		logger.info("Deleting customer with ID: {}", uuid);
		
		
		try {
			boolean retVal=customerServiceImpl.deleteCustomerById(uuid);
			return new ResponseEntity<String>("Deleted Customer ID : "+uuid,HttpStatus.OK);
		}
		catch (CustomerNotFoundException ex)
		{
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/")
	public ResponseEntity<String> deleteAllCustomers()
	{
		logger.info("Deleting all customers.");
		customerServiceImpl.deleteAllCustomer();
		return new ResponseEntity<String>("All Customers are Deleted",HttpStatus.OK);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handlingException(NullPointerException ex)
	{
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
