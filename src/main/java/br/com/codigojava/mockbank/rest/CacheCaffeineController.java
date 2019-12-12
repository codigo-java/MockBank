package br.com.codigojava.mockbank.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.service.CacheCaffeineService;

@RestController
@RequestMapping("/mockbank")
public class CacheCaffeineController {

	@Autowired
	private CacheCaffeineService cacheCaffeineService;
	
	@GetMapping(path = "/pessoa-cache")
	public Pessoa pessoaCacheCaffeine() {
		return cacheCaffeineService.pessoaCacheCaffeine();
	}

}
