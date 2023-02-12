package com.attornatus.challenge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.service.PersonService;

import lombok.AllArgsConstructor;

@RequestMapping("/persons")
@RestController
@AllArgsConstructor
public class PersonController {
	
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.ok(personService.findAllPersons());
	}
	
	@GetMapping("/names/{name}")
	public ResponseEntity<List<Person>> getPersonByName(@PathVariable String name) {
		return ResponseEntity.ok(personService.findPersonsByName(name));
	}
	
	@GetMapping("/{personId}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long personId) {
		return ResponseEntity.ok(personService.findPersonById(personId));
	}
	
	@PostMapping
	public ResponseEntity<Person> createAPerson(@RequestBody Person person) {
		return new ResponseEntity<Person>(personService.savePerson(person), HttpStatus.CREATED);
	}
	
}
