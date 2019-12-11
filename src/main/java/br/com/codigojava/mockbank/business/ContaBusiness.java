package br.com.codigojava.mockbank.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(ContaBusiness.class);
	
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

	public Conta findByAgenciaAndNumero(Long agencia, Long numero) {
		return contaRepository.findByAgenciaAndNumero(agencia, numero);
	}
	
	@Transactional
	public void transferir(TransferenciaDTO transferenciaDto) {
		Conta contaOrigem = contaRepository.findByAgenciaAndNumero(transferenciaDto.getAgencia(), transferenciaDto.getNumContaOrigem());
		if(contaOrigem == null) {
			LOG.error("MB-013: Conta {} não localizada para agência {} ", transferenciaDto.getNumContaOrigem(), transferenciaDto.getAgencia());
			throw new BusinessException("MB-013", transferenciaDto.getNumContaOrigem(), transferenciaDto.getAgencia());
		}
		
		SaqueDTO saque = new SaqueDTO(contaOrigem.getAgencia(), contaOrigem.getNumero(), transferenciaDto.getValor());

		validaLimite(contaOrigem, saque.getValor());
		sacar(saque);
		
		Conta contaDestino = contaRepository.findByAgenciaAndNumero(transferenciaDto.getAgencia(), transferenciaDto.getNumContaDestino());
		if(contaDestino == null) {
			LOG.error("MB-013: Conta {} não localizada para agência {} ", transferenciaDto.getNumContaOrigem(), transferenciaDto.getAgencia());
			throw new BusinessException("MB-013", transferenciaDto.getNumContaDestino(), transferenciaDto.getAgencia());			
		}
		
		DepositoDTO depositoDto = new DepositoDTO(contaDestino.getAgencia(), contaDestino.getNumero(), transferenciaDto.getValor());
		depositar(depositoDto);

		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);
	}
	
	public void depositar(DepositoDTO depositoDto) {
		Conta conta = contaRepository.findByAgenciaAndNumero(depositoDto.getAgencia(), depositoDto.getNumeroConta());
		if(conta == null) {
			LOG.error("MB-013: Conta {} não localizada para agência {} ", depositoDto.getNumeroConta(), depositoDto.getAgencia());
			throw new BusinessException("MB-013", depositoDto.getNumeroConta(), depositoDto.getAgencia());
		}
		
		if(depositoDto.getValor().compareTo(BigDecimal.valueOf(0)) > 0) {
			BigDecimal saldo = conta.getSaldo();
			saldo = saldo.add(depositoDto.getValor());
			conta.setSaldo(saldo);
			contaRepository.save(conta);
		} else {
			LOG.error("MB-014: Valor inválido para depósito");
			throw new BusinessException("MB-014", depositoDto.getNumeroConta(), depositoDto.getAgencia());
		}
	}
	
	public void sacar(SaqueDTO saqueDto){
		Conta conta = contaRepository.findByAgenciaAndNumero(saqueDto.getAgencia(), saqueDto.getNumeroConta());
		if(conta == null) {
			LOG.error("MB-013: Conta {} não localizada para agência {} ", saqueDto.getNumeroConta(), saqueDto.getAgencia());
			throw new BusinessException("MB-013", saqueDto.getNumeroConta(), saqueDto.getAgencia());
		}
		
		if(saqueDto.getValor().compareTo(BigDecimal.valueOf(0)) > 0) {
			validaLimite(conta, saqueDto.getValor());
			
			BigDecimal saldo = conta.getSaldo();
			saldo = saldo.subtract(saqueDto.getValor());
			conta.setSaldo(saldo);
			contaRepository.save(conta);
		} else {
			LOG.error("MB-014: Valor inválido para saque");
			throw new BusinessException("MB-014", saqueDto.getNumeroConta(), saqueDto.getAgencia());
		}
	}
	
	private void validaConta(Conta conta) {
		if(conta.getAgencia() == null || conta.getNumero() == null) {
			LOG.error("MB-009: Agência e Número são dados obrigatórios para Conta");
			throw new BusinessException("MB-009");
			
		} else if (conta.getTipoConta() != null) {
			
			if (conta.getTipoConta().equals(TipoConta.POUPANCA)) {
				validaPoupanca(conta);
			}else {
				validaContaCorrente(conta);
			}
			
		} else {
			LOG.error("MB-010: Tipo de Conta é obrigatório");
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
			LOG.error("MB-002: Tipo Conta Corrente de pessoa física não pode ter titulares PJ");
			throw new BusinessException("MB-002");
			
		} else 	if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PJ) && !titularesPF.isEmpty()) {
			LOG.error("MB-003: Tipo Conta Corrente de pessoa juridica não pode ter titulares PF");
			throw new BusinessException("MB-003");
			
		} else if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PF) && titularesPF.size() > 2) {
			LOG.error("MB-001: Conta Corrente de pessoa física pode ter no máximo 2 titulares");
			throw new BusinessException("MB-001");
			
		} else if(conta.getTipoConta().equals(TipoConta.CONTA_CORRENTE_PJ) && titularesPJ.size() > 1) {
			LOG.error("MB-004: Conta Corrente de pessoa juridica só pode ter apenas um titular");
			throw new BusinessException("MB-004");
			
		} else if (conta.getLimite().compareTo(BigDecimal.valueOf(0)) < 0){
			LOG.error("MB-008: O limite não pode receber valores negativos");
			throw new BusinessException("MB-008");			
		}
	}

	private void validaPoupanca(Conta conta) {
		List<Pessoa> titularesPJ = conta.getTitulares().stream()
														.filter(p -> p.getTipoPessoa().equals(TipoPessoa.JURIDICA))
														.collect(Collectors.toList());
		
		if(conta.getTipoConta().equals(TipoConta.POUPANCA) && conta.getTitulares().size() > 1) {
			LOG.error("MB-005: Conta Poupança só pode ter um titular");
			throw new BusinessException("MB-005");		
			
		} else if(conta.getTipoConta().equals(TipoConta.POUPANCA) && !titularesPJ.isEmpty()) {
			LOG.error("MB-006: Conta Poupança é permitido apenas para pessoas físicas");
			throw new BusinessException("MB-006");	
			
		} else if(conta.getTipoConta().equals(TipoConta.POUPANCA) && conta.getLimite() != null) {
			LOG.error("MB-007: Conta Poupança não pode ter limite definido");
			throw new BusinessException("MB-007");									
		}
	}

	public BigDecimal saldo(Long agencia, Long numero) {
		return contaRepository.findByAgenciaAndNumero(agencia, numero).getSaldo();
	}
	
	private void validaLimite(Conta conta, BigDecimal valorRetirado) {		
		BigDecimal limite = conta.getLimite();
		if(conta.getLimite() == null) {
			limite = BigDecimal.ZERO;
		}
		BigDecimal saldoComLimite = conta.getSaldo().add(limite);
		
		if(valorRetirado.compareTo(saldoComLimite) > 0) {
			LOG.error("MB-011: Operação não permitida. Limite excedido");
			throw new BusinessException("MB-011");						
		}		
	}

}
