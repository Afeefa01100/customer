package com.business.customer.entity;

import java.util.UUID;

public class Customer {
	
	public Customer() {
		super();
	}
	public Customer(UUID customerId, String customerFirstName, String customerMiddleName, String customerLastName,
			String customerEmail, String customerPhoneNumber) {
		super();
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerMiddleName = customerMiddleName;
		this.customerLastName = customerLastName;
		this.customerEmail = customerEmail;
		this.customerPhoneNumber = customerPhoneNumber;
	}
	private UUID customerId;
	private String customerFirstName;
	private String customerMiddleName;
	private String customerLastName;
	private String customerEmail;
	private String customerPhoneNumber;
	public UUID getCustomerId() {
		return customerId;
	}
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerMiddleName() {
		return customerMiddleName;
	}
	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerFirstName=" + customerFirstName + ", customerMiddleName="
				+ customerMiddleName + ", customerLastName=" + customerLastName + ", customerEmail=" + customerEmail
				+ ", customerPhoneNumber=" + customerPhoneNumber + "]";
	}

}
