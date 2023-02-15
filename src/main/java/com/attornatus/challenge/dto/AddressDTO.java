package com.attornatus.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	
	private Long id;
	
	private String publicArea;
	
	private String CEP;
	
	private Integer number;
	
	private String city;
}
