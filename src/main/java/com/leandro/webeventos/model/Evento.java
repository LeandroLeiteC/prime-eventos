package com.leandro.webeventos.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "evento")
public class Evento extends EntidadeBase {

	@NotBlank
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotBlank
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@NotBlank
	@Column(name = "pequena_descricao", nullable = false)
	private String pequenaDescricao;

	@NotBlank
	@Column(name = "casa_de_show", nullable = false)
	private String casaDeShow;

	@NotNull
	@Column(name = "data", nullable = false)
	private LocalDate data;

	@NotNull
	@Column(name = "preco", nullable = false)
	private double preco;

	@NotNull
	@Min(value = 1)
	@Column(name = "ingressos_disponiveis", nullable = false)
	private int ingressosDisponiveis;

	@NotNull
	@Min(value = 1)
	@Column(name = "limite_ingressos_por_cliente", nullable = false)
	private int limiteCliente;

	@Column(name = "imagemCard")
	private byte[] imagemCard;

	@Column(name = "imagemBanner")
	private byte[] imagemBanner;

	@Transient
	private String imagemCard64;

	public Evento() {
		super();
	}

	public Evento(Long id) {
		super.setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCasaDeShow() {
		return casaDeShow;
	}

	public void setCasaDeShow(String casaDeShow) {
		this.casaDeShow = casaDeShow;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getIngressosDisponiveis() {
		return ingressosDisponiveis;
	}

	public void setIngressosDisponiveis(int ingressosDisponiveis) {
		this.ingressosDisponiveis = ingressosDisponiveis;
	}

	public String getPequenaDescricao() {
		return pequenaDescricao;
	}

	public void setPequenaDescricao(String pequenaDescricao) {
		this.pequenaDescricao = pequenaDescricao;
	}

	public int getLimiteCliente() {
		return limiteCliente;
	}

	public void setLimiteCliente(int limiteCliente) {
		this.limiteCliente = limiteCliente;
	}

	public byte[] getImagemCard() {
		return imagemCard;
	}

	public void setImagemCard(byte[] imagemCard) {
		this.imagemCard = imagemCard;
	}

	public String getImagemCard64() {
		return imagemCard64;
	}

	public void setImagemCard64(String imagemCard64) {
		this.imagemCard64 = imagemCard64;
	}

	public byte[] getImagemBanner() {
		return imagemBanner;
	}

	public void setImagemBanner(byte[] imagemBanner) {
		this.imagemBanner = imagemBanner;
	}

}
