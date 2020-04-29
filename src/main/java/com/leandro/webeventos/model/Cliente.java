package com.leandro.webeventos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente extends EntidadeBase {

	@Column(name = "nome", unique = false, nullable = false)
	private String nome;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "cliente")
	private List<Compra> compras;

	public Cliente() {
		super();
	}

	public Cliente(Long id) {
		super.setId(id);
	}

}
