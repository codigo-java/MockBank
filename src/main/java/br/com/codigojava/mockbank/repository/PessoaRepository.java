package br.com.codigojava.mockbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codigojava.mockbank.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
