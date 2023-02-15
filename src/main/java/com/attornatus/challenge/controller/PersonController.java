package com.attornatus.challenge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RequestMapping("/persons")
@RestController
@AllArgsConstructor
@Api(tags = "Person Controller", description = "métodos HTTP para a entidade Pessoa")
public class PersonController {
	
	private PersonService personService;
	
	@ApiOperation(value = "Buscar todas as pessoas")
	@GetMapping
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.ok(personService.findAllPersons());
	}
	
	@ApiOperation(value = "Buscar pessoa pelo nome exato")
	@GetMapping("/names/{name}")
	public ResponseEntity<List<Person>> getPersonByName(@PathVariable String name) {
		return ResponseEntity.ok(personService.findPersonsByName(name));
	}
	
	@ApiOperation(value = "Buscar pessoa por id")
	@GetMapping("/{personId}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long personId) {
		return ResponseEntity.ok(personService.findPersonById(personId));
	}
	
	@ApiOperation(value = "Criar uma pessoa")
	@PostMapping
	public ResponseEntity<Person> createAPerson(@RequestBody Person person) {
		return new ResponseEntity<Person>(personService.savePerson(person), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar uma pessoa")
	@PutMapping("/{personId}")
	public ResponseEntity<Person> updatePerson(@PathVariable Long personId, @RequestBody Person updatedPerson) {
		return ResponseEntity.ok(personService.updatePerson(personId, updatedPerson));
	}
	
	@ApiOperation(value = "Excluir uma pessoa")
	@DeleteMapping("/{personId}")
	public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
		personService.deletePersonById(personId);
		return ResponseEntity.noContent().build();
	}
	
}
