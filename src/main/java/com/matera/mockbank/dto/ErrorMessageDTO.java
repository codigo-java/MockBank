package com.matera.mockbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorMessageDTO {

	private String code;
	private String message;
	
}
