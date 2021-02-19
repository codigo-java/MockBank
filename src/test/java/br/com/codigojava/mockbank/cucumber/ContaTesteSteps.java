package br.com.codigojava.mockbank.cucumber;

import java.math.BigDecimal;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.codigojava.mockbank.dto.DepositoDTO;
import br.com.codigojava.mockbank.entity.Conta;
import br.com.codigojava.mockbank.service.ContaService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class ContaTesteSteps {

	@Autowired
	private ContaService contaService;
	private Conta conta;

	@Dado("a conta criada para a agencia {int} e numero {int}")
	public void a_conta_criada_para_a_agencia_e_numero(Integer agencia, Integer numero) {
		conta = contaService.findByAgenciaAndNumero(agencia.longValue(), numero.longValue());
	}

	@Quando("realiza um deposito no valor de {int} na conta")
	public void realiza_um_deposito_no_valor_de_na_conta(Integer deposito) {
		contaService.depositar(new DepositoDTO(conta.getAgencia(), conta.getNumero(), new BigDecimal(deposito)));
	}

	@Entao("o saldo eh de {double} na conta")
	public void o_saldo_eh_de_na_conta(Double saldo) {
		Assert.assertEquals(Double.valueOf(1700.0), saldo);
	}
	
}
