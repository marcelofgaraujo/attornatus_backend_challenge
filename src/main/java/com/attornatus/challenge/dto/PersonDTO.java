// um objeto person dto que retorna os atributos da entidade person sem que haja interação com as relações
// retorna id, name, e birthDate

package com.attornatus.challenge.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	private Long id;
	
	@ApiModelProperty(example = "James Gosling")
	private String name;
	
	@ApiModelProperty(example = "19/05/1955")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
}
