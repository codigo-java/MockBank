package br.com.codigojava.mockbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codigojava.mockbank.business.CacheCaffeineBusiness;
import br.com.codigojava.mockbank.entity.Pessoa;

@Service
public class CacheCaffeineService {

	@Autowired
	private CacheCaffeineBusiness cacheCaffeineBusiness;
	
	public Pessoa pessoaCacheCaffeine() {
		return cacheCaffeineBusiness.pessoaCacheCaffeine();
	}
	
}
