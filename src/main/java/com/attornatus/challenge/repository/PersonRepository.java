package com.attornatus.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.challenge.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public List<Person> findByName(String name);
	
}
