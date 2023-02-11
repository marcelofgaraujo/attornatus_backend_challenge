package com.attornatus.challenge.entity;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Person {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personId")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "person")
	private Collection<Address> addresses;
	
	@OneToOne
	@JoinColumn(name = "principalAddress")
	private Address principalAddress;
	
}
