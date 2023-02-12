package com.attornatus.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AddressService {
	
	private AddressRepository addressRepository;
	private PersonRepository personRepository;
	
	public Address saveAddress(Address address) {
		addressRepository.save(address);
		return address;
	}
	
	public List<Address> findAllAddresses() {
		return addressRepository.findAll();
	}
	
	public Address findAddressById(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço não encontrado!"));
	}
	
	public List<Address> findPersonAddresses(Long personId) {
		Predicate<Address> addressesFilteredById = ad -> ad.getPerson().getId() == personId;
		List<Address> addresses = findAllAddresses();
		List<Address> personAddresses = addresses.stream()
				.filter(addressesFilteredById)
				.collect(Collectors.toList());
		return personAddresses;
	}
	
	public Address addAddressToAPerson(Long personId, Long addressId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		Address address = findAddressById(addressId);
		
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.getAddresses().add(address);
			return address;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada!");
		}
	}
	
	public Address setPrincipalAddress(Long personId, Long addressId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			Address address = findAddressById(addressId);
			person.setPrincipalAddress(address);
			return address;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada!");
		}
	}
	
	public Address findPrincipalAddress(Long personId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		if(personOpt.isPresent()) {
			return personOpt.get().getPrincipalAddress();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada!");
		}
	}
}
