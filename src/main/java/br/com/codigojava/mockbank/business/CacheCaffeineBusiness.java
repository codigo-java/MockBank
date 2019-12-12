package br.com.codigojava.mockbank.business;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.repository.PessoaRepository;

@Component
public class CacheCaffeineBusiness {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private static final String TESTE_CAFFEINE = "teste.caffeine";
	
	private Cache<String, Pessoa> cache = Caffeine.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS)
																.expireAfterWrite(30, TimeUnit.SECONDS)
																.maximumSize(3)
																.build();

	public Pessoa pessoaCacheCaffeine() {
		Pessoa pessoaCache = cache.getIfPresent(TESTE_CAFFEINE);		

		if(pessoaCache == null) {
			pessoaCache = pessoaRepository.findById(1).orElse(new Pessoa());
			cache.put(TESTE_CAFFEINE, pessoaCache);
		}else {
			pessoaCache = pessoaRepository.findById(2).orElse(new Pessoa());
			cache.put(TESTE_CAFFEINE, pessoaCache);			
		}		
		pessoaCache = cache.getIfPresent(TESTE_CAFFEINE);
		
		return pessoaCache;
	}
}
