package com.attornatus.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	
	@Column
	private String publicArea;
	
	@Column(length = 9)
	private String CEP;
	
	@Column
	private Integer number;
	
	@Column
	private String city;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "personId")
	private Person person;
	
}
