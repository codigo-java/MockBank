package com.matera.mockbank;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.matera.mockbank.business.PessoaBusiness;
import com.matera.mockbank.entity.Pessoa;
import com.matera.mockbank.entity.enums.TipoPessoa;
import com.matera.mockbank.repository.PessoaRepository;

@RunWith(EasyMockRunner.class)
public class MockBankApplicationTests {

	@TestSubject
	public PessoaBusiness pessoaBusiness = new PessoaBusiness();

	@Mock
	private PessoaRepository pessoaRepositoryMock;

	@Test
	public void listaPessoasTest() {
		List<Pessoa> lista = Arrays.asList(new Pessoa(1, "Aldeir", TipoPessoa.FISICA, 123456L));

		expect(pessoaRepositoryMock.findAll()).andReturn(lista);
		replay(pessoaRepositoryMock);

		List<Pessoa> pessoas = pessoaBusiness.findAll();
		for (Pessoa pessoa : pessoas) {
			System.out.println(pessoa);
		}
	}

}
