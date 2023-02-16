// interface utilizada para realizar toda a comunicação com a tabela address no banco de dados
// sem a necessidade de criar os comandos SQL

package com.attornatus.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.challenge.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
