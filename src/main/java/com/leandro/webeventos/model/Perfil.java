package com.leandro.webeventos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Entity
@Table(name = "perfil")
@Getter
@Setter
public class Perfil extends EntidadeBase {

	@Column(name = "descricao", nullable = false, unique = true)
	private String descricao;

	public Perfil() {
		super();
	}

	public Perfil(Long id) {
		super.setId(id);
	}

}
