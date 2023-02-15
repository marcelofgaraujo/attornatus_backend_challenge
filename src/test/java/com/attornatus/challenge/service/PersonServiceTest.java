package com.attornatus.challenge.service;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
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

}
