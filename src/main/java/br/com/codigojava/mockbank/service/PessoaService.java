package br.com.codigojava.mockbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codigojava.mockbank.business.PessoaBusiness;
import br.com.codigojava.mockbank.entity.Pessoa;

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

	public Pessoa findById(Integer id) {
		return pessoaBusiness.findById(id);
	}

}
