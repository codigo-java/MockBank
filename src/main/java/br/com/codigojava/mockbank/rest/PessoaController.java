package br.com.codigojava.mockbank.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codigojava.mockbank.dto.PessoaDTO;
import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.service.PessoaService;

@RestController
@RequestMapping("/mockbank")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping(path = "/pessoas")
	public List<Pessoa> getPessoas(){
		return pessoaService.findAll();
	}
	
	@GetMapping(path = "/pessoa/{id}")
	public Pessoa getPessoa(@PathVariable("id") Integer id){
		return pessoaService.findById(id);
	}
	
	@PostMapping(path = "/pessoa")
	public Pessoa insertPessoa(@RequestBody PessoaDTO pessoaDto) {
		return pessoaService.insertPessoa(pessoaDto.convertPessoa());
	}
}
