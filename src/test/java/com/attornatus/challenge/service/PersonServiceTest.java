// classe de teste para serviços de pessoa

package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.PersonRepository;

@SpringBootTest
class PersonServiceTest {
	
	// pessoa teste
	Person testPerson;
	
	// uso da anotação Mock do Mockito para mockar o repositório
	@Mock
	private PersonRepository personRepository;
	
	// uso da anotação InjectMocks para a injeção do mock na classe de serviço que
	// será testada
	@InjectMocks
	private PersonService personService;
	
	// setup acionado na inicialização de cada teste, instancia uma pessoa teste
	@BeforeEach
	public void init() {
		testPerson = new Person();
		testPerson.setName("joão das couves");
		testPerson.setBirthDate(Date.valueOf("1998-12-12"));
	}
	
	// teste para a função de salvar uma pessoa no banco
	@Test
	void savePersonTest() {
		// action
		Person returnPerson = personService.savePerson(testPerson);
		// assert
		verify(personRepository).save(testPerson);
		assertEquals(returnPerson, testPerson);
		assertEquals(returnPerson.getName(), testPerson.getName());
	}
	
	// teste para o retorno de todas as pessoas do banco
	@Test
	void findAllPersonsTest() {
		// arrange
		Person testPerson2 = new Person();
		testPerson2.setName("irmão do joão");
		testPerson2.setBirthDate(Date.valueOf("1922-11-11"));
		List<Person> expectReturn = Arrays.asList(testPerson, testPerson2);
		Mockito.when(personService.findAllPersons()).thenReturn(expectReturn);
		// action
		List<Person> listReturn = personService.findAllPersons();
		// assert
		verify(personRepository).findAll();
		assertEquals(listReturn.size(), 2);
		assertNotNull(listReturn);
	}
	
	// teste para o retorno de uma pessoa por id
	@Test
	void findPersonByIdTest() {
		// arrange
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Person personReturn = personService.findPersonById(1L);
		// assert
		verify(personRepository).findById(1L);
		assertEquals(personReturn.getName(), "joão das couves");
	}
	
	// teste para o retorno de uma pessoa que não existe
	// deve retornar 404 not found
	@Test
	void findPersonByInvalidId() {
		// arrange
		Long invalidId = 5L;
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			personService.findPersonById(invalidId);
		});
		// assert
		String expectMessage = "404 NOT_FOUND \"Pessoa não encontrada!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}
	
	// teste para o retorno de uma pessoa pelo nome exato
	@Test
	void findPersonByName() {
		// arrange
		Person newPerson = new Person();
		newPerson.setName("joão das couves");
		newPerson.setBirthDate(Date.valueOf("1925-11-12"));
		List<Person> personsList = Arrays.asList(testPerson, newPerson);
		Mockito.when(personRepository.findByNameContainingIgnoreCase("joão")).thenReturn(personsList);
		// action
		List<Person> returnList = personService.findPersonsByName("joão");
		// assert
		verify(personRepository).findByNameContainingIgnoreCase("joão");
		assertEquals(returnList.size(), personsList.size());
		assertNotNull(returnList);
	}
	
	// teste para atualizar uma pessoa
	@Test
	void updatePersonTest() {
		// arrange
		Person newPerson = new Person();
		newPerson.setName("john travolta");
		newPerson.setBirthDate(Date.valueOf("1922-11-11"));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Person resultPerson = personService.updatePerson(1L, newPerson);
		// assert
		verify(personRepository).findById(1L);
		assertEquals(resultPerson.getName(), "john travolta");
		assertEquals(resultPerson.getBirthDate(), Date.valueOf("1922-11-11"));
	}
	
	// teste para a exclusão de uma pessoa
	@Test
	void deletePersonTest() {
		// arrange
		Mockito.when(personRepository.existsById(1L)).thenReturn(true);
		// action
		personService.deletePersonById(1L);
		// assert
		List<Person> persons = personService.findAllPersons();
		assertEquals(persons.size(), 0);
	}

}
