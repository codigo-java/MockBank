package br.com.codigojava.mockbank.exception;

import java.text.MessageFormat;
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
	
	public BusinessException(String code, Object ... params) {
		this.code = code;
		this.message = format(getMessage(code), params);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public BusinessException(String code, HttpStatus httpStatus, Object ... params) {
		this.code = code;
		this.message = format(getMessage(code), params);
		this.httpStatus = httpStatus;
	}
	
	private String format(String message, Object ... args) {
		return new MessageFormat(message).format(args);
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
