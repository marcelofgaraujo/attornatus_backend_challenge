// um objeto address dto que retorna os atributos da entidade address sem que haja interação com as relações
// retorna id, publicArea, cep, number e city

package com.attornatus.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	
	private Long id;
	
	@ApiModelProperty(example = "Rua das Couves, bairro Verduras")
	private String publicArea;
	
	@ApiModelProperty(example = "35650-000")
	private String cep;
	
	@ApiModelProperty(example = "255")
	private Integer number;
	
	@ApiModelProperty(example = "Cidade Bacana")
	private String city;
}
