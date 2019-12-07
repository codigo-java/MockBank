package br.com.codigojava.mockbank.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.repository.PessoaRepository;

@Component
public class PessoaBusiness {

	@Autowired
	private PessoaRepository pessoaRepository;	
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	public Pessoa insertPessoa(Pessoa pessoa) {
		return pessoaRepository.saveAndFlush(pessoa);
	}

	public List<Pessoa> saveAll(List<Pessoa> pessoas) {
		return pessoaRepository.saveAll(pessoas);
	}

	public Pessoa findById(Integer id) {
		return pessoaRepository.findById(id).orElse(null);
	}

	
}
