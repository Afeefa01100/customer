package com.business.customer.dto;

import java.util.UUID;

import com.business.customer.entity.Customer;

public class CustomerDTO {
	
	public CustomerDTO(UUID uuid, Customer customer) {
		super();
		this.uuid = uuid;
		this.customer = customer;
	}
	public CustomerDTO() {
		super();
	}
	private UUID uuid;
	private Customer customer;
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "CustomerDTO [uuid=" + uuid + ", customer=" + customer + "]";
	}

}
