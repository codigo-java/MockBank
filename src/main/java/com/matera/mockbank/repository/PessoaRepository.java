package com.matera.mockbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matera.mockbank.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
