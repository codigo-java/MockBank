package br.com.codigojava.mockbank.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.codigojava.mockbank.dto.DepositoDTO;
import br.com.codigojava.mockbank.dto.SaqueDTO;
import br.com.codigojava.mockbank.dto.TransferenciaDTO;
import br.com.codigojava.mockbank.entity.Conta;
import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.entity.enums.TipoConta;
import br.com.codigojava.mockbank.entity.enums.TipoPessoa;
import br.com.codigojava.mockbank.exception.BusinessException;
import br.com.codigojava.mockbank.repository.ContaRepository;

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

	@Transactional
	public void transferir(TransferenciaDTO transferenciaDto) {
		Conta contaOrigem = contaRepository.findByAgenciaAndNumero(transferenciaDto.getAgencia(), transferenciaDto.getNumContaOrigem());
		if(contaOrigem == null) {
			throw new BusinessException("MB-013", transferenciaDto.getNumContaOrigem(), transferenciaDto.getAgencia());
		}
		SaqueDTO saqueDto = new SaqueDTO(contaOrigem.getAgencia(), contaOrigem.getNumero(), transferenciaDto.getValor());
		sacar(saqueDto);
		
		Conta contaDestino = contaRepository.findByAgenciaAndNumero(transferenciaDto.getAgencia(), transferenciaDto.getNumContaDestino());
		if(contaDestino == null) {
			throw new BusinessException("MB-013", transferenciaDto.getNumContaDestino(), transferenciaDto.getAgencia());			
		}
		DepositoDTO depositoDto = new DepositoDTO(contaDestino.getAgencia(), contaDestino.getNumero(), transferenciaDto.getValor());
		depositar(depositoDto);

		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);
	}
	
	public void depositar(DepositoDTO depositoDto) {
		Conta conta = contaRepository.findByAgenciaAndNumero(depositoDto.getAgencia(), depositoDto.getNumero());
		if(conta == null) {
			throw new BusinessException("MB-013", depositoDto.getNumero(), depositoDto.getAgencia());
		}
		
		if(depositoDto.getValor().compareTo(BigDecimal.valueOf(0)) > 0) {
			BigDecimal saldo = conta.getSaldo();
			saldo = saldo.add(depositoDto.getValor());
			conta.setSaldo(saldo);
			contaRepository.save(conta);
		} else {
			throw new BusinessException("MB-014", depositoDto.getNumero(), depositoDto.getAgencia());
		}
	}
	
	public void sacar(SaqueDTO saqueDto){
		Conta conta = contaRepository.findByAgenciaAndNumero(saqueDto.getAgencia(), saqueDto.getNumero());
		if(conta == null) {
			throw new BusinessException("MB-013", saqueDto.getNumero(), saqueDto.getAgencia());
		}
		
		if(saqueDto.getValor().compareTo(BigDecimal.valueOf(0)) > 0) {
			BigDecimal saldo = conta.getSaldo();
			saldo = saldo.subtract(saqueDto.getValor());
			conta.setSaldo(saldo);
			contaRepository.save(conta);
		} else {
			throw new BusinessException("MB-014", saqueDto.getNumero(), saqueDto.getAgencia());
		}
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

	public BigDecimal saldo(Long agencia, Long numero) {
		return contaRepository.findByAgenciaAndNumero(agencia, numero).getSaldo();
	}

}
