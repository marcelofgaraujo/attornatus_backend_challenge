package com.attornatus.challenge.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	public List<Person> findAllPersons() {
		return personRepository.findAll();
	}

	public Person findPersonById(Long personId) {
		return personRepository.findById(personId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));
	}

	public List<Person> findPersonsByName(String name) throws ResponseStatusException {
		List<Person> foundPersons = personRepository.findByName(name);
		if (foundPersons.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		} else {
			return foundPersons;
		}
	}
	
	public Person updatePerson(Long personId, Person updatedPerson) {
		Person person = findPersonById(personId);
		
		person.setName(updatedPerson.getName());
		person.setBirthDate(updatedPerson.getBirthDate());
		
		return savePerson(person);
	}
}
