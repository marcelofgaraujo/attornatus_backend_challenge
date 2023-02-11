package com.attornatus.challenge.service;

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
	
}
