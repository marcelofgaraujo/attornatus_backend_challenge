package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
	
//	Person testPerson = new Person(1L, "joão das couves", null, null, null);
//	Address testAddress = new Address(1L, "rua das couves, bairro centro", "45530-001", 244, "couves gerais", null);
//	Address testAddress2 = new Address(2L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais", null);
//	Address testAddress3 = new Address(3L, "rua da couve-de-bruxelas, bairro legume", "65530-001", 264, "couves gerais", null);
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private AddressRepository addressRepository;
	
	@InjectMocks
	private AddressService addressService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void saveAddressTest() {
		Address testAddress = new Address(1L, "rua das couves, bairro centro", "45530-001", 244, "couves gerais", null);
		
		Address returnAddress = addressService.saveAddress(testAddress);
		
		verify(addressRepository).save(testAddress);
		assertEquals(returnAddress, testAddress);
		assertEquals(returnAddress.getCity(), testAddress.getCity());
	}
	
	@Test
	void findAllAddresses() {
		Address testAddress = new Address(1L, "rua das couves, bairro centro", "45530-001", 244, "couves gerais", null);
		Address testAddress2 = new Address(2L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais", null);
		List<Address> expectReturn = Arrays.asList(testAddress, testAddress2);
		Mockito.when(addressService.findAllAddresses()).thenReturn(expectReturn);
		
		List<Address> actualReturn = addressService.findAllAddresses();
		
		verify(addressRepository).findAll();
		assertEquals(expectReturn, actualReturn);
		assertEquals(expectReturn.size(), actualReturn.size());
	}
	
	@Test
	void findAddressByIdTest() {
		Address testAddress = new Address(1L, "rua das couves, bairro centro", "45530-001", 244, "couves gerais", null);
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		
		Address addressReturn = addressService.findAddressById(1L);
		
		verify(addressRepository).findById(1L);
		assertEquals(addressReturn.getCEP(), "45530-001");
		assertEquals(addressReturn.getPublicArea(), "rua das couves, bairro centro");
		assertEquals(addressReturn.getCity(), "couves gerais");
	}

}
