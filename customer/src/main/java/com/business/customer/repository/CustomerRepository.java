package com.business.customer.repository;

//create table Customer(
//		 customer_Id UUID PRIMARY KEY,
//		customer_first_name VARCHAR(255),
//		customer_middle_name VARCHAR(255),
//		customer_last_name VARCHAR(255),
//		customer_email VARCHAR(255) UNIQUE,
//		customer_phone_number VARCHAR(255)
//		);



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.business.customer.entity.Customer;

@Repository
public class CustomerRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Customer> getAllCustomers()
	{
		logger.info("Fetching all customers from the database.");
		String sql="Select * from Customer";
		return jdbcTemplate.query(sql, this::mapCustomer);
	}
	
	@SuppressWarnings("deprecation")
	public Customer getCustomerByCustomerId(UUID uuid)
	{
		logger.info("Attempting to fetch customer with ID: {}", uuid);
		String sql="Select * from Customer where customer_id =?";
		List<Customer> lcustomer=jdbcTemplate.query(sql, new Object[] {uuid},this::mapCustomer);
		
		if(lcustomer.size()>0)
		{
			return lcustomer.get(0);
		}
		
		return null;
	}
	
	
	public Customer createCustomer(Customer customer)
	{
		logger.info("Creating customer: {} {}", customer.getCustomerFirstName(), customer.getCustomerLastName());
		String sql="INSERT into Customer(customer_id,customer_first_name,customer_middle_name,customer_last_name,customer_email,customer_phone_number) values(?,?,?,?,?,?)";
		int reVal=jdbcTemplate.update(sql,UUID.randomUUID(),customer.getCustomerFirstName(),customer.getCustomerMiddleName(),customer.getCustomerLastName(),customer.getCustomerEmail(),customer.getCustomerPhoneNumber());
		if(reVal>0)
		{
			return customer;
		}
		
		return null;
	}
	
	public int deleteCustomerByCustomerId(UUID uuid)
	{
		logger.info("Attempting to delete customer with ID: {}", uuid);
		String sql="delete from Customer where customer_id=?";
		return jdbcTemplate.update(sql, uuid);
	}
	
	public int deleteAllCustomers()
	{
		logger.info("Attempting to delete all customers from the database.");
		String sql="delete from Customer";
		return jdbcTemplate.update(sql);
	}
	
	public Customer updateCustomer(UUID uuid,Customer customer)
	{
		logger.info("Attempting to update customer with ID: {}", uuid);
		String sql="UPDATE Customer set customer_first_name=?,customer_middle_name=?,customer_last_name=?,customer_email=?,customer_phone_number=? where customer_id=?";
		int reVal=jdbcTemplate.update(sql,customer.getCustomerFirstName(),customer.getCustomerMiddleName(),customer.getCustomerLastName(),customer.getCustomerEmail(),customer.getCustomerPhoneNumber(),uuid);
		if(reVal>0)
		{
			return customer;
		}
		
		return null;
	}
	
	
	
	
	private Customer mapCustomer(ResultSet rs,int rowNums) throws SQLException
	{
		Customer customer=new Customer();
		
		customer.setCustomerId(UUID.fromString(rs.getString("customer_id")));
		customer.setCustomerFirstName(rs.getString("customer_first_name"));
		customer.setCustomerMiddleName(rs.getString("customer_middle_name"));
		customer.setCustomerLastName(rs.getString("customer_last_name"));
		customer.setCustomerEmail(rs.getString("customer_email"));
		customer.setCustomerPhoneNumber(rs.getString("customer_phone_number"));
		
		return customer;
	}

}
