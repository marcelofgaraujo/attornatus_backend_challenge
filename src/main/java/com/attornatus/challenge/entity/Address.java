// entidade address usada pra salvar os endereços no banco
// salva id, publicArea, cep, number e city
// daqui também será gerada uma relação manyToOne com a tabela person

package com.attornatus.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class Address {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addressId")
	private Long id;
	
	@NotBlank
	@Column
	private String publicArea;
	
	@NotBlank
	@Column(length = 9)
	private String cep;
	
	@NotNull
	@Column
	private Integer number;
	
	@NotBlank
	@Size(max = 150)
	@Column
	private String city;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "personId")
	private Person person;
	
}
