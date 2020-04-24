package com.leandro.webeventos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "casa_de_show")
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getLimitePessoas() {
		return limitePessoas;
	}

	public void setLimitePessoas(int limitePessoas) {
		this.limitePessoas = limitePessoas;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "CasaShow [nome=" + nome + ", cep=" + cep + ", uf=" + uf + ", cidade=" + cidade + ", bairro=" + bairro
				+ ", rua=" + rua + ", numero=" + numero + ", limitePessoas=" + limitePessoas + ", telefone=" + telefone
				+ "]";
	}

}
