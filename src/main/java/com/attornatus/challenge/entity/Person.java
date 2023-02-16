// entidade person usada pra salvar as pessoas no banco
// salva id, name e birthDate
// daqui também serão geradas uma relação oneToMany (atributo addresses) 
// e uma relação oneToOne (atributo principalAddress) com a tabela address

package com.attornatus.challenge.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Person {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personId")
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column
	private String name;
	
	@NotNull
	@Column(length = 10)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Address> addresses = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	@JoinColumn(name = "principalAddressId")
	private Address principalAddress;
	
}
