package com.matera.mockbank.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matera.mockbank.dto.ContaDTO;
import com.matera.mockbank.entity.Conta;
import com.matera.mockbank.exception.BusinessException;
import com.matera.mockbank.service.ContaService;

@RestController
@RequestMapping("/mockbank")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@GetMapping(path = "/contas")
	public List<Conta> getContas() {
		return contaService.findAll();
	}

	@PostMapping(path = "conta")
	public Conta insertConta(@RequestBody ContaDTO contaDto) {
		return contaService.insertConta(contaDto.convertConta());
	}

	@GetMapping(path = "teste-be")
	public Conta testeBubinessException() {
		throw new BusinessException("MB-001");
	}

}
