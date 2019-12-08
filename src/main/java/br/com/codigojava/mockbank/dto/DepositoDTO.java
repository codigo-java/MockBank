package br.com.codigojava.mockbank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositoDTO {

	private Long agencia;
	private Long numero;
	private BigDecimal valor;

}
