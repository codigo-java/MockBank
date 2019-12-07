package com.matera.mockbank.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matera.mockbank.dto.PessoaDTO;
import com.matera.mockbank.entity.Pessoa;
import com.matera.mockbank.service.PessoaService;

@RestController
@RequestMapping("/mockbank")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping(path = "/pessoas")
	public List<Pessoa> getPessoas(){
		return pessoaService.findAll();
	}
	
	@PostMapping(path = "pessoa")
	public Pessoa insertPessoa(@RequestBody PessoaDTO pessoaDto) {
		return pessoaService.insertPessoa(pessoaDto.convertPessoa());
	}
}
