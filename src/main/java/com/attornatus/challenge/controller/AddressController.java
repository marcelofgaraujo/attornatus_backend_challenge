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

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.service.AddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RequestMapping("/addresses")
@RestController
@AllArgsConstructor
@Api(tags = "Address Controller", description = "métodos HTTP para a entidade Endereço")
public class AddressController {
	
	private AddressService addressService;
	
	@ApiOperation(value = "Buscar todos os endereços")
	@GetMapping
	public ResponseEntity<List<Address>> getAllAddresses() {
		return ResponseEntity.ok(addressService.findAllAddresses());
	}
	
	@ApiOperation(value = "Buscar endereço por id")
	@GetMapping("/{addressId}")
	public ResponseEntity<Address> getAddressById(@PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.findAddressById(addressId));
	}
	
	@ApiOperation(value = "Adicionar um endereço a uma pessoa")
	@GetMapping("/add/{personId}/{addressId}")
	public ResponseEntity<Address> addPersonAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.addAddressToAPerson(personId, addressId));
	}
	
	@ApiOperation(value = "Definir um endereço principal a uma pessoa")
	@GetMapping("/setprincipaladdress/{personId}/{addressId}")
	public ResponseEntity<Address> addPrincipalAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.setPrincipalAddress(personId, addressId));
	}
	
	@ApiOperation(value = "Buscar endereço principal de uma pessoa")
	@GetMapping("/principaladdress/{personId}")
	public ResponseEntity<Address> getPrincipalAddress(@PathVariable Long personId) {
		return ResponseEntity.ok(addressService.findPrincipalAddress(personId));
	}
	
	@ApiOperation(value = "Buscar todos os endereços de uma pessoa")
	@GetMapping("/useraddresses/{personId}")
	public ResponseEntity<List<Address>> getAllPersonAddresses(@PathVariable Long personId) {
		return ResponseEntity.ok(addressService.findPersonAddresses(personId));
	}
	
	@ApiOperation(value = "Criar um endereço")
	@PostMapping
	public ResponseEntity<Address> createAnAddress(@RequestBody Address address) {
		return new ResponseEntity<Address>(addressService.saveAddress(address), HttpStatus.CREATED);	
	}
	
	@ApiOperation(value = "Editar um endereço")
	@PutMapping("/{addressId")
	public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address updatedAddress) {
		return ResponseEntity.ok(addressService.updateAddress(addressId, updatedAddress));
	}
	
	@ApiOperation(value = "Excluir um endereço")
	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
		addressService.deleteAddressById(addressId);
		return ResponseEntity.noContent().build();
	}

}
