package com.matera.mockbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matera.mockbank.business.PessoaBusiness;
import com.matera.mockbank.entity.Pessoa;

@Service
public class PessoaService {

	@Autowired
	private PessoaBusiness pessoaBusiness;
		
	public List<Pessoa> findAll() {
		return pessoaBusiness.findAll();
	}

	public Pessoa insertPessoa(Pessoa pessoa) {
		return pessoaBusiness.insertPessoa(pessoa);
	}

}
