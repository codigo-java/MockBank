package com.matera.mockbank.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matera.mockbank.entity.Conta;
import com.matera.mockbank.entity.Pessoa;
import com.matera.mockbank.entity.enums.TipoConta;
import com.matera.mockbank.entity.enums.TipoPessoa;
import com.matera.mockbank.exception.BusinessException;
import com.matera.mockbank.repository.ContaRepository;

@Component
public class ContaBusiness {

	@Autowired
	private ContaRepository contaRepository;

	public List<Conta> findAll() {
		return contaRepository.findAll();
	}

	public Conta insertConta(Conta conta) {
		validaConta(conta);
		return contaRepository.saveAndFlush(conta);
	}

	public List<Conta> saveAll(List<Conta> contas) {
		return contaRepository.saveAll(contas);
	}

	public void transferirValor(Long agencia, Long numContaOrigem, Long numContaDestino) {
		
	}
	
	public void depositar(Long agencia, Long numero) {
		
	}
	
	public void sacar(Long agencia, Long numero){
		
	}
	
	private void validaConta(Conta conta) {
		if(conta.getAgencia() == null || conta.getNumero() == null) {
			throw new BusinessException("MB-009");
			
		} else if (conta.getTipoConta() != null) {
			
			if (conta.getTipoConta().equals(TipoConta.POUPANCA)) {
				validaPoupanca(conta);
			}else {
				validaContaCorrente(conta);
			}
			
		} else {
			throw new BusinessException("MB-010");
		}
	}

	private void validaContaCorrente(Conta conta) {
		List<Pessoa> titularesPJ = conta.getTitulares().stream()
				 										.filter(p -> p.getTipoPessoa().equals(TipoPessoa.JURIDICA))
				 										.collect(Collectors.toList());
		List<Pessoa> titularesPF = conta.getTitulares().stream()
														.filter(p -> p.getTipoPessoa().equals(TipoPessoa.FISICA))
														.collect(Collectors.toList());
		
		if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PF) && !titularesPJ.isEmpty()) {
			throw new BusinessException("MB-002");
			
		} else 	if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PJ) && !titularesPF.isEmpty()) {
			throw new BusinessException("MB-003");
			
		} else if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PF) && titularesPF.size() > 2) {
			throw new BusinessException("MB-001");
			
		} else if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PJ) && titularesPJ.size() > 1) {
			throw new BusinessException("MB-004");
			
		} else if (conta.getLimite().compareTo(BigDecimal.valueOf(0)) < 0){
			throw new BusinessException("MB-008");			
		}
	}

	private void validaPoupanca(Conta conta) {
		List<Pessoa> titularesPJ = conta.getTitulares().stream()
														.filter(p -> p.getTipoPessoa().equals(TipoPessoa.JURIDICA))
														.collect(Collectors.toList());
		
		if(conta.getTipoConta().equals(TipoConta.POUPANCA) && conta.getTitulares().size() > 1) {
			throw new BusinessException("MB-005");		
			
		} else if(conta.getTipoConta().equals(TipoConta.POUPANCA) && !titularesPJ.isEmpty()) {
			throw new BusinessException("MB-006");	
			
		} else if(conta.getTipoConta().equals(TipoConta.POUPANCA) && conta.getLimite() != null) {
			throw new BusinessException("MB-007");									
		}
	}

}
