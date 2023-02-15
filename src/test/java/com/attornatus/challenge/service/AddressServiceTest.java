package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

@SpringBootTest
class AddressServiceTest {
		
	Person testPerson;
	Address testAddress;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private AddressRepository addressRepository;
	
	@InjectMocks
	private AddressService addressService;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		testPerson = new Person();
		testPerson.setName("joão das couves");
		testPerson.setBirthDate(new Date("12/12/1998"));
		
		testAddress = new Address();
		testAddress.setPublicArea("rua das couves, bairro centro");
		testAddress.setCEP("45530-001");
		testAddress.setNumber(244);
		testAddress.setCity("cidade das couves");
	}
	
	@Test
	void saveAddressTest() {
		// action
		Address returnAddress = addressService.saveAddress(testAddress);
		// assert
		verify(addressRepository).save(testAddress);
		assertEquals(returnAddress, testAddress);
		assertEquals(returnAddress.getCity(), testAddress.getCity());
	}
	
	@Test
	void findAllAddresses() {
		// arrange
		Address testAddress2 = new Address(2L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais", null);
		List<Address> expectReturn = Arrays.asList(testAddress, testAddress2);
		Mockito.when(addressService.findAllAddresses()).thenReturn(expectReturn);
		// action
		List<Address> listReturn = addressService.findAllAddresses();
		// assert
		verify(addressRepository).findAll();
		assertEquals(listReturn.size(), 2);
		assertNotNull(listReturn);
	}
	
	@Test
	void findAddressByIdTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		// action
		Address addressReturn = addressService.findAddressById(1L);
		// assert
		verify(addressRepository).findById(1L);
		assertEquals(addressReturn.getCEP(), "45530-001");
		assertEquals(addressReturn.getPublicArea(), "rua das couves, bairro centro");
		assertEquals(addressReturn.getCity(), "cidade das couves");
	}
	
	@Test
	void addAddressToAPersonTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Address result = addressService.addAddressToAPerson(1L, 1L);
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository).findById(1L);
		assertEquals(result, testAddress);
		assertTrue(testPerson.getAddresses().contains(result));
	}
	
	@Test
	void findPersonAddressesTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		List<Address> result = addressService.findPersonAddresses(1L);
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		assertTrue(result.containsAll(testPerson.getAddresses()));
	}

}
