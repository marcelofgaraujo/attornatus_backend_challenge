package com.attornatus.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.challenge.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
