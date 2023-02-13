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

import com.attornatus.challenge.entity.Address;
import com.attornatus.challenge.service.AddressService;

import lombok.AllArgsConstructor;

@RequestMapping("/addresses")
@RestController
@AllArgsConstructor
public class AddressController {
	
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<Address>> getAllAddresses() {
		return ResponseEntity.ok(addressService.findAllAddresses());
	}
	
	@GetMapping("/add/{personId}/{addressId}")
	public ResponseEntity<Address> addPersonAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.addAddressToAPerson(personId, addressId));
	}
	
	@GetMapping("/setprincipaladdress/{personId}/{addressId}")
	public ResponseEntity<Address> addPrincipalAddress(@PathVariable Long personId, @PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.setPrincipalAddress(personId, addressId));
	}
	
	@GetMapping("/principaladdress/{personId}")
	public ResponseEntity<Address> getPrincipalAddress(@PathVariable Long personId) {
		return ResponseEntity.ok(addressService.findPrincipalAddress(personId));
	}
	
	@GetMapping("/useraddresses/{personId}")
	public ResponseEntity<List<Address>> getAllPersonAddresses(@PathVariable Long personId) {
		return ResponseEntity.ok(addressService.findPersonAddresses(personId));
	}
	
	@PostMapping
	public ResponseEntity<Address> createAnAddress(@RequestBody Address address) {
		return new ResponseEntity<Address>(addressService.saveAddress(address), HttpStatus.CREATED);	
	}

}
