package com.matera.mockbank.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.matera.mockbank.entity.Conta;
import com.matera.mockbank.entity.Pessoa;
import com.matera.mockbank.entity.enums.TipoConta;

import lombok.Data;

@Data
public class ContaDTO {

	private Integer id;
	private Long agencia;
	private Long numero;
	private Set<Pessoa> titulares;
	private TipoConta tipoConta;
	private BigDecimal saldo;
	private BigDecimal limite;

	public Conta convertConta() {
		Conta conta = new Conta();
		conta.setId(this.id);
		conta.setAgencia(this.agencia);
		conta.setNumero(this.numero);
		conta.setTipoConta(this.tipoConta);
		conta.setSaldo(this.saldo);
		conta.setLimite(this.limite);
		conta.setTitulares(this.titulares);
		return conta;
	}
}
