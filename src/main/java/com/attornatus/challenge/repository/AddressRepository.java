package com.attornatus.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.challenge.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
