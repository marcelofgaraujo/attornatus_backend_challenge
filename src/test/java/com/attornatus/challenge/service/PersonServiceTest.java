package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

}
