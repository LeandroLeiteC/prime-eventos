package com.leandro.webeventos.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "casa_de_show")
@Getter
@Setter
public class CasaShow extends EntidadeBase {

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cep", nullable = false)
	private String cep;

	@Column(name = "uf", nullable = false)
	private String uf;

	@Column(name = "cidade", nullable = false)
	private String cidade;

	@Column(name = "bairro", nullable = false)
	private String bairro;

	@Column(name = "rua", nullable = false)
	private String rua;

	@Column(name = "numero", nullable = false)
	private int numero;

	@Column(name = "limite_de_pessoas", nullable = false)
	private int limitePessoas;

	@Column(name = "telefone", nullable = false)
	private String telefone;

	@OneToMany(mappedBy = "casaDeShow")
	private List<Evento> eventos;
	
	public CasaShow() {
		super();
	}

	public CasaShow(Long id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "CasaShow [nome=" + nome + ", cep=" + cep + ", uf=" + uf + ", cidade=" + cidade + ", bairro=" + bairro
				+ ", rua=" + rua + ", numero=" + numero + ", limitePessoas=" + limitePessoas + ", telefone=" + telefone
				+ "]";
	}

}
