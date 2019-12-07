package com.matera.mockbank.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.matera.mockbank.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private Long agencia;

	@Column
	private Long numero;

    @ManyToMany(fetch=FetchType.EAGER)
	private Set<Pessoa> titulares;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;

	@Column
	private BigDecimal saldo;

	@Column
	private BigDecimal limite;
	
}
