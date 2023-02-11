package com.attornatus.challenge.service;

import org.springframework.stereotype.Service;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.repository.AddressRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AddressService {
	
	private AddressRepository addressRepository;
	
	public Address saveAddress(Address address) {
		addressRepository.save(address);
		return address;
	}
}
