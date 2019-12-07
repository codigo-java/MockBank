package com.matera.mockbank.exception.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.matera.mockbank.dto.ErrorMessageDTO;
import com.matera.mockbank.exception.BusinessException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BusinessException.class })
	public ResponseEntity<ErrorMessageDTO> responseBusinessException(BusinessException ex) {
		ErrorMessageDTO error = new ErrorMessageDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(error, ex.getHttpStatus());
	}

}
