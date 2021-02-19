package br.com.codigojava.mockbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.codigojava.mockbank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer>, JpaSpecificationExecutor<Conta> {

	public Conta findByAgenciaAndNumero(Long agencia, Long numero);
	
}
