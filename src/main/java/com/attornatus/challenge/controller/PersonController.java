// endpoints para os serviços de pessoa

package com.attornatus.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.attornatus.challenge.dto.PersonDTO;
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
	private ModelMapper modelMapper;
	
	@ApiOperation(value = "Buscar todas as pessoas")
	@GetMapping
	public ResponseEntity<List<PersonDTO>> getAllPersons() {
		List<Person> persons = personService.findAllPersons();
		return ResponseEntity.ok(convertListPersonToListDTO(persons));
	}
	
	@ApiOperation(value = "Buscar pessoa pelo nome exato")
	@GetMapping("/names/{name}")
	public ResponseEntity<List<PersonDTO>> getPersonByName(@PathVariable String name) {
		List<Person> foundPersons = personService.findPersonsByName(name);
		return ResponseEntity.ok(convertListPersonToListDTO(foundPersons));
	}
	
	@ApiOperation(value = "Buscar pessoa por id")
	@GetMapping("/{personId}")
	public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long personId) {
		Person foundPerson = personService.findPersonById(personId);
		return ResponseEntity.ok(convertPersonToDTO(foundPerson));
	}
	
	@ApiOperation(value = "Criar uma pessoa")
	@PostMapping
	public ResponseEntity<PersonDTO> createAPerson(@RequestBody PersonDTO personDTO) {
		Person person = convertDTOtoPerson(personDTO);
		personService.savePerson(person);
		return new ResponseEntity<PersonDTO>(convertPersonToDTO(person), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Editar uma pessoa")
	@PutMapping("/{personId}")
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long personId, @RequestBody PersonDTO updatedPersonDTO) {
		Person updatedPerson = convertDTOtoPerson(updatedPersonDTO);
		personService.updatePerson(personId, updatedPerson);
		return ResponseEntity.ok(convertPersonToDTO(updatedPerson));
	}
	
	@ApiOperation(value = "Excluir uma pessoa")
	@DeleteMapping("/{personId}")
	public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
		personService.deletePersonById(personId);
		return ResponseEntity.noContent().build();
	}
	
	// converte uma entidade person em um objeto person dto
	private PersonDTO convertPersonToDTO(Person person) {
		return modelMapper.map(person, PersonDTO.class);
	}
	
	// converte um objeto person dto em uma entidade person
	private Person convertDTOtoPerson(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Person.class);
	}
	
	// converte uma lista de entidades person em uma lista de objetos person dto
	private List<PersonDTO> convertListPersonToListDTO(List<Person> persons) {
		List<PersonDTO> personsDTO = new ArrayList<>();
		persons.forEach(p -> {
			personsDTO.add(convertPersonToDTO(p));
		});
		
		return personsDTO;
	}
	
}
