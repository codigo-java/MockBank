package com.matera.mockbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matera.mockbank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

}
