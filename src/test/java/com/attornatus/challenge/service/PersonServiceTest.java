package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.PersonRepository;

@SpringBootTest
class PersonServiceTest {

	Person testPerson;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonService personService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		testPerson = new Person();
		testPerson.setName("joão das couves");
		testPerson.setBirthDate(new Date("12/12/1998"));
	}

	@Test
	void savePersonTest() {
		// action
		Person returnPerson = personService.savePerson(testPerson);
		// assert
		verify(personRepository).save(testPerson);
		assertEquals(returnPerson, testPerson);
		assertEquals(returnPerson.getName(), testPerson.getName());
	}

	@SuppressWarnings("deprecation")
	@Test
	void findAllPersonsTest() {
		// arrange
		Person testPerson2 = new Person();
		testPerson2.setName("irmão do joão");
		testPerson2.setBirthDate(new Date("11/11/1922"));
		List<Person> expectReturn = Arrays.asList(testPerson, testPerson2);
		Mockito.when(personService.findAllPersons()).thenReturn(expectReturn);
		// action
		List<Person> listReturn = personService.findAllPersons();
		// assert
		verify(personRepository).findAll();
		assertEquals(listReturn.size(), 2);
		assertNotNull(listReturn);
	}

}
