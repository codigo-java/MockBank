package br.com.codigojava.mockbank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoDTO {

	private Long agencia;
	private Long numeroConta;
	private BigDecimal valor;

}
