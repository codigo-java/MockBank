package br.com.codigojava.mockbank.dto;

import br.com.codigojava.mockbank.entity.Pessoa;
import br.com.codigojava.mockbank.entity.enums.TipoPessoa;
import lombok.Data;

@Data
public class PessoaDTO {

	private Integer id;
	private String nome;
	private TipoPessoa tipoPessoa;
	private Long inscricao;
	
	public Pessoa convertPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(this.id);
		pessoa.setNome(this.nome);
		pessoa.setInscricao(this.inscricao);
		pessoa.setTipoPessoa(this.tipoPessoa);
		return pessoa  ;
	}
}
