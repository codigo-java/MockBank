package br.com.codigojava.mockbank.exception.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.codigojava.mockbank.dto.ErrorMessageDTO;
import br.com.codigojava.mockbank.exception.BusinessException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BusinessException.class })
	public ResponseEntity<ErrorMessageDTO> responseBusinessException(BusinessException ex) {
		ErrorMessageDTO error = new ErrorMessageDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(error, ex.getHttpStatus());
	}

}
