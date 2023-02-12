package com.attornatus.challenge.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	
	public List<Address> findAllAddresses() {
		return addressRepository.findAll();
	}
	
	public List<Address> findPersonAddresses(Long personId) {
		Predicate<Address> addressesFilteredById = ad -> ad.getPerson().getId() == personId;
		List<Address> addresses = findAllAddresses();
		List<Address> personAddresses = addresses.stream()
				.filter(addressesFilteredById)
				.collect(Collectors.toList());
		return personAddresses;
	}
}
