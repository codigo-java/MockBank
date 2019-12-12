package br.com.codigojava.mockbank.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codigojava.mockbank.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	@Cacheable("cache.pessoa")
	public Optional<Pessoa> findById(Integer id);
	
}
