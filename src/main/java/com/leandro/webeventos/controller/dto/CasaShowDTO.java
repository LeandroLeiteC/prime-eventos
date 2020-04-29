package com.leandro.webeventos.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.leandro.webeventos.model.CasaShow;

public class CasaShowDTO {

	@NotBlank
	private String nome;

	@NotBlank
	private String cep;

	@NotBlank
	private String uf;

	@NotBlank
	private String cidade;

	@NotBlank
	private String bairro;

	@NotBlank
	private String rua;

	@NotNull
	@Min(value = 1)
	private int numero;

	@NotNull
	@Min(value = 1)
	private int limitePessoas;

	@NotBlank
	private String telefone;

	public CasaShow transforma() {
		CasaShow casaShow = new CasaShow();
		casaShow.setBairro(bairro);
		casaShow.setCep(cep);
		casaShow.setCidade(cidade);
		casaShow.setLimitePessoas(limitePessoas);
		casaShow.setNome(nome);
		casaShow.setNumero(numero);
		casaShow.setNumero(numero);
		casaShow.setRua(rua);
		casaShow.setTelefone(telefone);
		casaShow.setUf(uf);
		return casaShow;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

}
