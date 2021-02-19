package br.com.codigojava.mockbank.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codigojava.mockbank.business.ContaBusiness;
import br.com.codigojava.mockbank.dto.DepositoDTO;
import br.com.codigojava.mockbank.dto.FindContaDTO;
import br.com.codigojava.mockbank.dto.SaqueDTO;
import br.com.codigojava.mockbank.dto.TransferenciaDTO;
import br.com.codigojava.mockbank.entity.Conta;

@Service
public class ContaService {

	@Autowired
	private ContaBusiness contaBusiness;

	public List<Conta> findAll() {
		return contaBusiness.findAll();
	}
	
	public Conta findByAgenciaAndNumero(Long agencia, Long numero) {
		return contaBusiness.findByAgenciaAndNumero(agencia, numero);
	}

	public List<Conta> findAll(Long numberAccount, Long bankBranch, String accountType) {
		FindContaDTO findContaDTO = new FindContaDTO();
		findContaDTO.setNumberAccount(numberAccount);
		findContaDTO.setBankBranch(bankBranch);
		findContaDTO.setAccountType(accountType);

		return contaBusiness.findAll(findContaDTO);
	}

	public Conta insertConta(Conta conta) {
		return contaBusiness.insertConta(conta);
	}

	public void transferir(TransferenciaDTO transferenciaDto) {
		contaBusiness.transferir(transferenciaDto);
	}

	public void depositar(DepositoDTO depositoDto) {
		contaBusiness.depositar(depositoDto);
	}

	public void sacar(SaqueDTO saqueDto) {
		contaBusiness.sacar(saqueDto);
	}

	public BigDecimal saldo(Long agencia, Long numero) {
		return contaBusiness.saldo(agencia, numero);
	}

}
