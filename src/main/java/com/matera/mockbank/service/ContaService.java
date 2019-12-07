package com.matera.mockbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matera.mockbank.business.ContaBusiness;
import com.matera.mockbank.entity.Conta;

@Service
public class ContaService {

	@Autowired
	private ContaBusiness contaBusiness;
		
	public List<Conta> findAll() {
		return contaBusiness.findAll();
	}

	public Conta insertConta(Conta conta) {
		return contaBusiness.insertConta(conta);
	}

}
