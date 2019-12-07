package com.matera.mockbank.exception;

import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;
	
	private static final ResourceBundle resourse = ResourceBundle.getBundle("error_messages");

	public BusinessException(String code) {
		this.code = code;
		this.message = getMessage(code);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public BusinessException(String code, HttpStatus httpStatus) {
		this.code = code;
		this.message = getMessage(code);
		this.httpStatus = httpStatus;
	}
	
	public BusinessException (String code, String message){
		this.code = code;
		this.message = message;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	private String getMessage(String code) {
		String msg;
		try {
			msg = resourse.getString(code);
		} catch (Exception e) {
			return "Message not found for code " + code;
		}
		return msg;
	}
}
