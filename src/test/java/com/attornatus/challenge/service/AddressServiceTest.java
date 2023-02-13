package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AddressServiceTest {
	
	Person testPerson = new Person(1L, "joão das couves", null, null, null);
	Address testAddress = new Address(1L, "rua das couves, bairro centro", "45530-001", 244, "couves gerais", null);
	Address testAddress2 = new Address(1L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais", null);
	Address testAddress3 = new Address(1L, "rua da couve-de-bruxelas, bairro legume", "65530-001", 264, "couves gerais", null);
	
	@MockBean
	private PersonRepository personRepository;
	
	@MockBean
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Test
	void saveAddressTest() {
		Address returnAddress = addressService.saveAddress(testAddress);
		System.out.println(returnAddress.toString());
		assertEquals(returnAddress, testAddress);
	}

}
