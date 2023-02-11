package com.attornatus.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.PersonRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class PersonService {
	
	private PersonRepository personRepository;
	
	public Person savePerson(Person person) {
		personRepository.save(person);
		return person;
	}
	
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}
	
}
