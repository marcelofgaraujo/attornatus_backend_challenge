package com.attornatus.challenge.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	private Long id;
	
	private String name;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
}
