package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

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
	
	@BeforeEach
	public void init() {
		testPerson = new Person();
		testPerson.setName("joão das couves");
		testPerson.setBirthDate(Date.valueOf("1998-12-12"));
		
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
	
	@Test void findAddressByInvalidIdTest() {
		// arrange
		Long invalidId = 3L;
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findAddressById(invalidId);
		});
		// assert
		String expectMessage = "404 NOT_FOUND \"Endereço não encontrado!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
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
	void addAddressToAPersonThatContainsThisAddress() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.addAddressToAPerson(1L, 1L);
		});
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		String expectedMessage = "400 BAD_REQUEST \"Endereço já pertence à esta pessoa!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void findPersonAddressesWhenItsNullTest() {
		// arrange
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findPersonAddresses(1L);
		});
		// assert
		verify(personRepository).findById(1L);
		String expectMessage = "404 NOT_FOUND \"Esta pessoa não contém endereços cadastrados!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
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
	
	@Test
	void setPrincipalAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Address result = addressService.setPrincipalAddress(1L, 1L);
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		assertEquals(testPerson.getPrincipalAddress(), result);
		assertEquals(testPerson.getPrincipalAddress().getCity(), "cidade das couves");
	}
	
	@Test
	void findPrincipalAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.setPrincipalAddress(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Address result = addressService.findPrincipalAddress(1L);
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(3)).findById(1L);
		assertEquals(testPerson.getPrincipalAddress(), result);
		assertEquals(testPerson.getPrincipalAddress().getCEP(), "45530-001");
	}
	
	@Test
	void updateAddressTest() {
		// arrange
		Address newAddress = new Address();
		newAddress.setCEP("25550-354");
		newAddress.setNumber(456);
		newAddress.setCity("couves gerais");
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		// action
		Address resultAddress = addressService.updateAddress(1L, newAddress);
		// assert
		verify(addressRepository).findById(1L);
		assertEquals(resultAddress.getCity(), "couves gerais");
		assertEquals(resultAddress.getCEP(), "25550-354");
		assertEquals(resultAddress.getNumber(), 456);
	}
	
	@Test
	void deleteAddressTest() {
		// arrange
		Mockito.when(addressRepository.existsById(1L)).thenReturn(true);
		// action
		addressService.deleteAddressById(1L);
		// assert
		List<Address> addresses = addressService.findAllAddresses();
		assertEquals(addresses.size(), 0);
	}

}
