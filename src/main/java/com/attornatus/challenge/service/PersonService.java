// classe de serviços para operações relacionadas com pessoas

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
	
	// repositório necessário para o funcionamento da classe
	private PersonRepository personRepository;
	
	// salva uma pessoa no banco
	public Person savePerson(Person person) {
		personRepository.save(person);
		return person;
	}
	
	// retorna uma lista com todas as pessoas
	public List<Person> findAllPersons() {
		return personRepository.findAll();
	}
	
	// retorna uma pessoa por id no banco
	public Person findPersonById(Long personId) {
		return personRepository.findById(personId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));
	}
	
	// retorna uma pessoa pelo nome exato
	public List<Person> findPersonsByName(String name) throws ResponseStatusException {
		List<Person> foundPersons = personRepository.findByName(name);
		if (foundPersons.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		} else {
			return foundPersons;
		}
	}
	
	// atualiza uma pessoa
	public Person updatePerson(Long personId, Person updatedPerson) {
		Person person = findPersonById(personId);
		
		person.setName(updatedPerson.getName());
		person.setBirthDate(updatedPerson.getBirthDate());
		
		return savePerson(person);
	}
	
	// exclui uma pessoa
	public void deletePersonById(Long personId) throws ResponseStatusException {
		if(!personRepository.existsById(personId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		} else {
			personRepository.deleteById(personId);
		}
	}
}
