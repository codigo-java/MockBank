package com.matera.mockbank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.matera.mockbank.entity.enums.TipoPessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique = true)
	private String nome;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(unique = true)
	private Long inscricao;

}
