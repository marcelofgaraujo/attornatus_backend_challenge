// interface utilizada para realizar toda a comunicação com a tabela person no banco de dados
// sem a necessidade de criar os comandos SQL

package com.attornatus.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.challenge.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public List<Person> findByNameContainingIgnoreCase(String name);
	
}
