// classe de teste para serviços de endereço

package com.attornatus.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

@SpringBootTest
class AddressServiceTest {

	// pessoa e endereço teste
	Person testPerson;
	Address testAddress;

	// uso da anotação Mock do Mockito para mockar os repositórios
	@Mock
	private PersonRepository personRepository;

	@Mock
	private AddressRepository addressRepository;

	// uso da anotação InjectMocks para a injeção dos mocks na classe de serviço que
	// será testada
	@InjectMocks
	private AddressService addressService;

	// setup acionado na inicialização de cada teste, instancia pessoa e endereço
	// teste
	@BeforeEach
	public void init() {
		testPerson = new Person();
		testPerson.setName("joão das couves");
		testPerson.setBirthDate(Date.valueOf("1998-12-12"));

		testAddress = new Address();
		testAddress.setPublicArea("rua das couves, bairro centro");
		testAddress.setCep("45530-001");
		testAddress.setNumber(244);
		testAddress.setCity("cidade das couves");
	}

	// teste para a função de salvar no banco
	@Test
	void saveAddressTest() {
		// action
		Address returnAddress = addressService.saveAddress(testAddress);
		// assert
		verify(addressRepository).save(testAddress);
		assertEquals(returnAddress, testAddress);
		assertEquals(returnAddress.getCity(), testAddress.getCity());
	}

	// teste para o retorno de todos os endereços do banco
	@Test
	void findAllAddresses() {
		// arrange
		Address testAddress2 = new Address(2L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais",
				null);
		List<Address> expectReturn = Arrays.asList(testAddress, testAddress2);
		Mockito.when(addressService.findAllAddresses()).thenReturn(expectReturn);
		// action
		List<Address> listReturn = addressService.findAllAddresses();
		// assert
		verify(addressRepository).findAll();
		assertEquals(listReturn.size(), 2);
		assertNotNull(listReturn);
	}
	
	// teste para uma busca paginada de endereços
	@SuppressWarnings("unused")
	@Test
	void addressPagedSearch() {
		// arrange
		Address testAddress2 = new Address(2L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais",
				null);
		Address testAddress3 = new Address(3L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais",
				null);
		// este ultimo testAddress nao deve aparecer na busca
		Address testAddress4 = new Address(4L, "rua do brócolis, bairro couve", "55530-001", 254, "couves gerais",
				null);
		Pageable page = PageRequest.of(0, 3);
		List<Address> expectReturn = Arrays.asList(testAddress, testAddress2, testAddress3);
		Page<Address> pageResult = new PageImpl<>(expectReturn);
		Mockito.when(addressService.findAddressesByPage(0, 3)).thenReturn(pageResult);
		// action
		Page<Address> pagedReturn = (Page<Address>) addressService.findAddressesByPage(0, 3);
		List<Address> listReturn = pagedReturn.getContent();
		// assert
		verify(addressRepository).findAll(page);
		assertEquals(expectReturn.size(), listReturn.size());
	}

	// teste para o retorno de um endereço por id
	@Test
	void findAddressByIdTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		// action
		Address addressReturn = addressService.findAddressById(1L);
		// assert
		verify(addressRepository).findById(1L);
		assertEquals(addressReturn.getCep(), "45530-001");
		assertEquals(addressReturn.getPublicArea(), "rua das couves, bairro centro");
		assertEquals(addressReturn.getCity(), "cidade das couves");
	}

	// teste para o retorno de um endereço por um id de endereço que não existe
	// deve retornar 404 not found
	@Test
	void findAddressByInvalidIdTest() {
		// arrange
		Long invalidId = 3L;
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findAddressById(invalidId);
		});
		// assert
		String expectMessage = "404 NOT_FOUND \"Endereço não encontrado!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}

	// teste para a adição de um endereço a uma pessoa
	@Test
	void addAddressToAPersonTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Address result = addressService.addAddressToAPerson(1L, 1L);
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository).findById(1L);
		assertEquals(result, testAddress);
		assertTrue(testPerson.getAddresses().contains(result));
	}

	// teste para buscar os endereços de uma pessoa
	@Test
	void findPersonAddressesTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		// action
		List<Address> result = addressService.findPersonAddresses(1L);
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		assertTrue(result.containsAll(testPerson.getAddresses()));
	}

	// teste para a adição de um endereço a uma pessoa que já contém esse endereço
	// deve retornar 400 bad request
	@Test
	void addAddressToAPersonThatContainsThisAddress() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.addAddressToAPerson(1L, 1L);
		});
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		String expectedMessage = "400 BAD_REQUEST \"Endereço já pertence à esta pessoa!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	// teste para a adição de um endereço que já pertence a outra pessoa
	// deve retornar 400 bad request
	@Test
	void addAddressThatBelongsToOtherPersonTest() {
		// arrange
		Person newPerson = new Person();
		newPerson.setId(3L);
		newPerson.setName("irmão do joão");
		newPerson.setBirthDate(Date.valueOf("1988-3-3"));
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(3L)).thenReturn(Optional.of(newPerson));
		Mockito.when(addressService.addAddressToAPerson(3L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.addAddressToAPerson(1L, 1L);
		});
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository).findById(1L);
		verify(personRepository).findById(3L);
		String expectedMessage = "400 BAD_REQUEST \"Este endereço já pertence à outra pessoa!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	// teste para a busca de endereços de uma pessoa que não contém endereços
	// deve retornar 404 not found
	@Test
	void findPersonAddressesWhenItsNullTest() {
		// arrange
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findPersonAddresses(1L);
		});
		// assert
		verify(personRepository).findById(1L);
		String expectMessage = "404 NOT_FOUND \"Esta pessoa não contém endereços cadastrados!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}

	// teste para a busca de endereços de uma pessoa que não existe
	// deve retornar 404 not found
	@Test
	void findPersonAddressesWhenPersonIsNullTest() {
		// arrange
		Long invalidId = 3L;
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findPersonAddresses(invalidId);
		});
		// assert
		String expectMessage = "404 NOT_FOUND \"Pessoa não encontrada!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}

	// teste para definir o endereço principal de uma pessoa
	@Test
	void setPrincipalAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		// action
		Address result = addressService.setPrincipalAddress(1L, 1L);
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		assertEquals(testPerson.getPrincipalAddress(), result);
		assertEquals(testPerson.getPrincipalAddress().getCity(), "cidade das couves");
	}

	// teste para a busca de endereço principal de uma pessoa
	@Test
	void findPrincipalAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		Mockito.when(addressService.setPrincipalAddress(1L, 1L)).thenReturn(testAddress);
		// action
		Address result = addressService.findPrincipalAddress(1L);
		// assert
		verify(addressRepository, Mockito.times(2)).findById(1L);
		verify(personRepository, Mockito.times(3)).findById(1L);
		assertEquals(testPerson.getPrincipalAddress(), result);
		assertEquals(testPerson.getPrincipalAddress().getCep(), "45530-001");
	}

	// teste para a remoção de um endereço principal de uma pessoa
	@Test
	void removePrincipalAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		Mockito.when(addressService.addAddressToAPerson(1L, 1L)).thenReturn(testAddress);
		Mockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(testPerson);
		// action
		addressService.removePrincipalAddress(1L);
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository, Mockito.times(2)).findById(1L);
		assertEquals(testPerson.getPrincipalAddress(), null);
	}

	// teste para definir o endereço principal de uma pessoa que não contém este endereço
	// deve retornar 400 bad request
	@Test
	void setPrincipalAddressWhenPersonDontHaveThisAddressTest() {
		// arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.setPrincipalAddress(1L, 1L);
		});
		// assert
		verify(addressRepository).findById(1L);
		verify(personRepository).findById(1L);
		String expectMessage = "400 BAD_REQUEST \"Este endereço não pertence à pessoa!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}
	
	// teste para a busca de endereço principal de uma pessoa que não tem um endereço principal definido
	// deve retornar 404 not found
	@Test
	void findPrincipalAddressWhenPersonDontHaveAnyTest() {
		// arrange
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));
		// action
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			addressService.findPrincipalAddress(1L);
		});
		verify(personRepository).findById(1L);
		String expectMessage = "404 NOT_FOUND \"Pessoa não possui endereço principal!\"";
		String actualMessage = exception.getMessage();
		assertEquals(expectMessage, actualMessage);
	}
	
	// teste para atualizar um endereço
	@Test
	void updateAddressTest() {
		// arrange
		Address newAddress = new Address();
		newAddress.setCep("25550-354");
		newAddress.setNumber(456);
		newAddress.setCity("couves gerais");
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));
		// action
		Address resultAddress = addressService.updateAddress(1L, newAddress);
		// assert
		verify(addressRepository).findById(1L);
		assertEquals(resultAddress.getCity(), "couves gerais");
		assertEquals(resultAddress.getCep(), "25550-354");
		assertEquals(resultAddress.getNumber(), 456);
	}
	
	// teste para a exclusão de um endereço
	@Test
	void deleteAddressTest() {
		// arrange
		Mockito.when(addressRepository.existsById(1L)).thenReturn(true);
		// action
		addressService.deleteAddressById(1L);
		// assert
		List<Address> addresses = addressService.findAllAddresses();
		assertEquals(addresses.size(), 0);
	}

}
