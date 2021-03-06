package br.com.codigojava.mockbank.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.codigojava.mockbank.dto.ContaDTO;
import br.com.codigojava.mockbank.dto.DepositoDTO;
import br.com.codigojava.mockbank.dto.SaqueDTO;
import br.com.codigojava.mockbank.dto.TransferenciaDTO;
import br.com.codigojava.mockbank.entity.Conta;
import br.com.codigojava.mockbank.exception.BusinessException;
import br.com.codigojava.mockbank.service.ContaService;

@RestController
@RequestMapping("/mockbank")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping(path = "/contas")
    public List<Conta> getContas() {
	return contaService.findAll();
    }

    @GetMapping(path = "/conta")
    public List<Conta> findContas(@RequestParam(value = "numberAccount", required = false) Long numberAccount, 
	    			  @RequestParam(value = "bankBranch", required = false) Long bankBranch,
	    			  @RequestParam(value = "accountType", required = false) String accountType) {
	return contaService.findAll(numberAccount, bankBranch, accountType);
    }

    @PostMapping(path = "conta")
    public Conta insertConta(@RequestBody ContaDTO contaDto) {
	return contaService.insertConta(contaDto.convertConta());
    }

    @PostMapping(path = "/conta/transferencia")
    public void trasnferir(@RequestBody TransferenciaDTO transferenciaDto) {
	contaService.transferir(transferenciaDto);
    }

    @PostMapping(path = "/conta/deposito")
    public void depositar(@RequestBody DepositoDTO depositoDto) {
	contaService.depositar(depositoDto);
    }

    @PostMapping(path = "/conta/saque")
    public void sacar(@RequestBody SaqueDTO saqueDto) {
	contaService.sacar(saqueDto);
    }

    @GetMapping(path = "/conta/saldo/{agencia}/{numero}")
    public BigDecimal saldo(@PathVariable(name = "agencia") Long agencia, @PathVariable(name = "numero") Long numero) {
	return contaService.saldo(agencia, numero);
    }

    @GetMapping(path = "/teste-be")
    public Conta testeBubinessException() {
	// throw new BusinessException("MB-001");
	throw new BusinessException("MB-013", "123548", "001");
    }

}
