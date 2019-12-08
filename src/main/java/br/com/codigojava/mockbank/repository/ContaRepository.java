package br.com.codigojava.mockbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codigojava.mockbank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

	public Conta findByAgenciaAndNumero(Long agencia, Long numero);
	
}
