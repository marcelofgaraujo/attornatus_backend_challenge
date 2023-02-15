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

import com.attornatus.challenge.dto.AddressDTO;
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
	private ModelMapper modelMapper;
	
	@ApiOperation(value = "Buscar todos os endereços")
	@GetMapping
	public ResponseEntity<List<AddressDTO>> getAllAddresses() {
		List<Address> addresses = addressService.findAllAddresses();
		return ResponseEntity.ok(convertListAddressToListDTO(addresses));
	}
	
	@ApiOperation(value = "Buscar endereço por id")
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
		Address address = addressService.findAddressById(addressId);
		return ResponseEntity.ok(convertAddressToDTO(address));
	}
	
	@ApiOperation(value = "Adicionar um endereço a uma pessoa")
	@GetMapping("/add/{personId}/{addressId}")
	public ResponseEntity<AddressDTO> addPersonAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		Address address = addressService.addAddressToAPerson(personId, addressId);
		return ResponseEntity.ok(convertAddressToDTO(address));
	}
	
	@ApiOperation(value = "Definir um endereço principal a uma pessoa")
	@GetMapping("/setprincipaladdress/{personId}/{addressId}")
	public ResponseEntity<AddressDTO> addPrincipalAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		Address address = addressService.setPrincipalAddress(personId, addressId);
		return ResponseEntity.ok(convertAddressToDTO(address));
	}
	
	@ApiOperation(value = "Buscar endereço principal de uma pessoa")
	@GetMapping("/principaladdress/{personId}")
	public ResponseEntity<AddressDTO> getPrincipalAddress(@PathVariable Long personId) {
		Address address = addressService.findPrincipalAddress(personId);
		return ResponseEntity.ok(convertAddressToDTO(address));
	}
	
	@ApiOperation(value = "Buscar todos os endereços de uma pessoa")
	@GetMapping("/useraddresses/{personId}")
	public ResponseEntity<List<AddressDTO>> getAllPersonAddresses(@PathVariable Long personId) {
		List<Address> addresses = addressService.findPersonAddresses(personId);
		return ResponseEntity.ok(convertListAddressToListDTO(addresses));
	}
	
	@ApiOperation(value = "Criar um endereço")
	@PostMapping
	public ResponseEntity<AddressDTO> createAnAddress(@RequestBody AddressDTO addressDTO) {
		Address address = convertDTOtoAddress(addressDTO);
		addressService.saveAddress(address);
		return new ResponseEntity<AddressDTO>(convertAddressToDTO(address), HttpStatus.CREATED);	
	}
	
	@ApiOperation(value = "Editar um endereço")
	@PutMapping("/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO updatedAddressDTO) {
		Address updatedAddress = convertDTOtoAddress(updatedAddressDTO);
		addressService.updateAddress(addressId, updatedAddress);
		return ResponseEntity.ok(convertAddressToDTO(updatedAddress));
	}
	
	@ApiOperation(value = "Excluir um endereço")
	@DeleteMapping("/{addressId}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
		addressService.deleteAddressById(addressId);
		return ResponseEntity.noContent().build();
	}
	
	private AddressDTO convertAddressToDTO(Address address) {
		return modelMapper.map(address, AddressDTO.class);
	}
	
	private Address convertDTOtoAddress(AddressDTO addressDTO) {
		return modelMapper.map(addressDTO, Address.class);
	}
	
	private List<AddressDTO> convertListAddressToListDTO(List<Address> addresses) {
		List<AddressDTO> addressesDTO = new ArrayList<>();
		addresses.forEach(ad -> {
			addressesDTO.add(convertAddressToDTO(ad));
		});
		
		return addressesDTO;
	}

}
