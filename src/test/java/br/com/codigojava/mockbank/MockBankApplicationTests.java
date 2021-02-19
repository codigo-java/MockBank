package br.com.codigojava.mockbank;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.codigojava.mockbank.business.ContaBusiness;
import br.com.codigojava.mockbank.business.PessoaBusiness;
import br.com.codigojava.mockbank.dto.SaqueDTO;
import br.com.codigojava.mockbank.dto.TransferenciaDTO;
import br.com.codigojava.mockbank.entity.Conta;
import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.entity.enums.TipoConta;
import br.com.codigojava.mockbank.entity.enums.TipoPessoa;
import br.com.codigojava.mockbank.exception.BusinessException;
import br.com.codigojava.mockbank.repository.ContaRepository;
import br.com.codigojava.mockbank.repository.PessoaRepository;

@RunWith(EasyMockRunner.class)
public class MockBankApplicationTests {

	@TestSubject
	public PessoaBusiness pessoaBusiness = new PessoaBusiness();
	
	@TestSubject
	public ContaBusiness contaBusiness = new ContaBusiness();

	@Mock
	private PessoaRepository pessoaRepositoryMock;
	
	@Mock
	private ContaRepository contaRepositoryMock;

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
	
	//MB-001: Conta Corrente de pessoa física pode ter no máximo 2 titulares
	//@Test(expected = BusinessException.class)
	@Test
	public void contaCorrentePessoaFisica() {
		Set<Pessoa> titulares = new HashSet<>();
		titulares.add(new Pessoa(1, "Fulano", TipoPessoa.FISICA, Long.valueOf(12345678901234L)));
		titulares.add(new Pessoa(2, "Ciclano", TipoPessoa.FISICA, Long.valueOf(42345678901234L)));
		titulares.add(new Pessoa(3, "Beltrano", TipoPessoa.FISICA, Long.valueOf(32345678901234L)));
		
		Conta contaCorrente = new Conta(Integer.valueOf(1), Long.valueOf(1), Long.valueOf(123456), titulares, TipoConta.CONTA_CORRENTE_PF, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
		
		try {
			contaBusiness.insertConta(contaCorrente);
		} catch (BusinessException e) {
			assertEquals("MB-001", e.getCode());
		}
	}
	
	// MB-013: Conta X não localizada para agência Y
	@Test
	public void contaNaoLocalizada() {
		expect(contaRepositoryMock.findByAgenciaAndNumero(EasyMock.anyLong(), EasyMock.anyLong())).andReturn(null);
		replay(contaRepositoryMock);
		
		TransferenciaDTO transferenciaDto = new TransferenciaDTO(1L, 111111L, 222222L, BigDecimal.valueOf(2500));
		try {
			contaBusiness.transferir(transferenciaDto);
		} catch (BusinessException e) {
			assertEquals("MB-013", e.getCode());
		}
	}
	
	//MB-005: Conta Poupança só pode ter um titular
	@Test
	public void validaContaPoupancaComDoisTitulares() {
		Set<Pessoa> titulares = new HashSet<>();
		titulares.add(new Pessoa(1, "Fulano", TipoPessoa.FISICA, Long.valueOf(12345678901234L)));
		titulares.add(new Pessoa(2, "Ciclano", TipoPessoa.FISICA, Long.valueOf(42345678901234L)));
		
		Conta poupanca = new Conta(1, Long.valueOf(1), 123456L, titulares, TipoConta.POUPANCA, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
		
		try {
			contaBusiness.insertConta(poupanca);
		} catch (BusinessException e) {
			assertEquals("MB-005", e.getCode());
		}
	}
	
	//MB-011 = Operação não permitida. Limite excedido
	@Test
	public void validaLimiteExcedidoAoSacar() {
		Set<Pessoa> titulares = new HashSet<>();
		titulares.add(new Pessoa(1, "Fulano", TipoPessoa.FISICA, Long.valueOf(12345678901234L)));
		
		Conta contaCorrente = new Conta(1, 1L, 123456L, titulares, TipoConta.CONTA_CORRENTE_PF, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));

		expect(contaRepositoryMock.findByAgenciaAndNumero(1L, 123456L)).andReturn(contaCorrente);
		replay(contaRepositoryMock);
		
		SaqueDTO saque = new SaqueDTO(1L, 123456L, BigDecimal.valueOf(10000));
		
		try {
			contaBusiness.sacar(saque);
		} catch (BusinessException e) {
			assertEquals("MB-011", e.getCode());
		}
	}
	
	//MB-011 = Operação não permitida. Limite excedido
	@Test
	public void validaLimiteExcedidoAoTransferir() {
		Set<Pessoa> titulares = new HashSet<>();
		titulares.add(new Pessoa(1, "Fulano", TipoPessoa.FISICA, Long.valueOf(12345678901234L)));
		
		Conta contaCorrente = new Conta(1, 1L, 123456L, titulares, TipoConta.CONTA_CORRENTE_PF, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));

		expect(contaRepositoryMock.findByAgenciaAndNumero(1L, 123456L)).andReturn(contaCorrente);
		replay(contaRepositoryMock);
		
		TransferenciaDTO transferenciaDTO = new TransferenciaDTO(1L, 123456L, 111111L, BigDecimal.valueOf(10000));
		try {
			contaBusiness.transferir(transferenciaDTO);
		} catch (BusinessException e) {
			assertEquals("MB-011", e.getCode());
		}
	}

}
