// classe de serviços para operações relacionadas com endereços

package com.attornatus.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.entity.Person;
import com.attornatus.challenge.repository.AddressRepository;
import com.attornatus.challenge.repository.PersonRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AddressService {
	
	// repositórios necessários para o funcionamento da classe
	private AddressRepository addressRepository;
	private PersonRepository personRepository;
	
	// salva um endereço no banco
	public Address saveAddress(Address address) {
		addressRepository.save(address);
		return address;
	}
	
	// retorna uma lista com todos os endereços
	public List<Address> findAllAddresses() {
		return addressRepository.findAll();
	}
	
	// busca paginada de endereços
	public Iterable<Address> findAddressesByPage(int pageNumber, int quantityPerPage) {
		if (quantityPerPage > 3) quantityPerPage = 3;
		Pageable page = PageRequest.of(pageNumber, quantityPerPage);
		return addressRepository.findAll(page);
	}
	
	// retorna um endereço por id no banco
	public Address findAddressById(Long addressId) {
		return addressRepository.findById(addressId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!"));
	}
	
	// retorna os endereços de uma pessoa
	public List<Address> findPersonAddresses(Long personId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);

		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			if (person.getAddresses().isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esta pessoa não contém endereços cadastrados!");
			return person.getAddresses();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		}
	}
	
	// adiciona um endereço a uma pessoa
	public Address addAddressToAPerson(Long personId, Long addressId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		Address address = findAddressById(addressId);

		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			
			boolean haveThisAddress = person.getAddresses().contains(address);
			if (haveThisAddress) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço já pertence à esta pessoa!");
			if (address.getPerson() != null && !haveThisAddress) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este endereço já pertence à outra pessoa!");
			
			person.getAddresses().add(address);
			address.setPerson(person);
			personRepository.save(person);
			return address;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		}
	}
	
	// define endereço principal a uma pessoa (a pessoa deve ser dona do endereço)
	public Address setPrincipalAddress(Long personId, Long addressId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);

		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			Address address = findAddressById(addressId);
			boolean addressBelongsToPerson = person.getAddresses().contains(address);

			if (addressBelongsToPerson) {
				person.setPrincipalAddress(address);
				personRepository.save(person);
				return person.getPrincipalAddress();
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este endereço não pertence à pessoa!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		}
	}
	
	// retorna o endereço principal de uma pessoa, se definido
	public Address findPrincipalAddress(Long personId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		if (personOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		} else if (personOpt.get().getPrincipalAddress() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não possui endereço principal!");
		}
		return personOpt.get().getPrincipalAddress();
	}
	
	// remove o endereço principal de uma pessoa
	public void removePrincipalAddress(Long personId) throws ResponseStatusException {
		Optional<Person> personOpt = personRepository.findById(personId);
		if (personOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!");
		} else {
			Person person = personOpt.get();
			person.setPrincipalAddress(null);
			personRepository.save(person);
		}
	}
	
	// atualiza um endereço
	public Address updateAddress(Long addressId, Address updatedAddress) {
		Address address = findAddressById(addressId);

		address.setPublicArea(updatedAddress.getPublicArea());
		address.setCep(updatedAddress.getCep());
		address.setNumber(updatedAddress.getNumber());
		address.setCity(updatedAddress.getCity());

		return saveAddress(address);
	}
	
	// exclui um endereço
	public void deleteAddressById(Long addressId) throws ResponseStatusException {
		if (!addressRepository.existsById(addressId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
		} else {
			addressRepository.deleteById(addressId);
		}
	}
}
