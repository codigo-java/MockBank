package br.com.codigojava.mockbank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferenciaDTO {

	private Long agencia;
	private Long numContaOrigem;
	private Long numContaDestino;
	private BigDecimal valor;
	
}
