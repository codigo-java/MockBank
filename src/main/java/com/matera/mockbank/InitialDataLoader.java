package com.matera.mockbank;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.matera.mockbank.business.ContaBusiness;
import com.matera.mockbank.business.PessoaBusiness;
import com.matera.mockbank.entity.Conta;
import com.matera.mockbank.entity.Pessoa;
import com.matera.mockbank.entity.enums.TipoConta;
import com.matera.mockbank.entity.enums.TipoPessoa;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PessoaBusiness pessoaBusiness;
	
	@Autowired
	private ContaBusiness contaBusiness;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		cadastraPessoas();
		cadastraContas();
	}

	@Transactional
	private void cadastraPessoas() {
		List<Pessoa> pessoas = Arrays.asList(
			new Pessoa(1, "Fulano", TipoPessoa.FISICA, 11111111111L),
			new Pessoa(2, "Ciclano", TipoPessoa.FISICA, 22222222222L),
			new Pessoa(3, "Matera", TipoPessoa.JURIDICA, 12345678901234L)
		);
		
		pessoaBusiness.saveAll(pessoas);
	}
	
	@Transactional
	private void cadastraContas() {
		Set<Pessoa> titularConta1 = new HashSet<>();		
		titularConta1.add(pessoaBusiness.findById(1));
		
		Set<Pessoa> titularesConta2 = new HashSet<>();		
		titularesConta2.add(pessoaBusiness.findById(1));
		titularesConta2.add(pessoaBusiness.findById(2));

		Set<Pessoa> titularConta3 = new HashSet<>();		
		titularConta3.add(pessoaBusiness.findById(3));
		
		List<Conta> contas = Arrays.asList(
				new Conta(1, 1L, 111111L, titularConta1, TipoConta.POUPANCA, BigDecimal.valueOf(1500), null),
				new Conta(2, 1L, 111222L, titularesConta2, TipoConta.CONTA_CORRENTE_PF, BigDecimal.valueOf(2800), BigDecimal.valueOf(4000)),
				new Conta(3, 1L, 333333L, titularConta3, TipoConta.CONTA_CORRENTE_PJ, BigDecimal.valueOf(17500), BigDecimal.valueOf(13000))
		);

		contaBusiness.saveAll(contas);
	}

}
