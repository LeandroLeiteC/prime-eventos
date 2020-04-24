package com.leandro.webeventos.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.leandro.webeventos.model.Evento;

public class EventoDTO {

	@NotBlank
	private String nome;

	@NotBlank
	private String descricao;

	@NotBlank
	private String pequenaDescricao;

	@NotBlank
	private String casaDeShow;

	@NotBlank
	private String data;

	@NotNull
	private double preco;

	@NotNull
	@Min(value = 1)
	private int ingressosDisponiveis;

	@NotNull
	@Min(value = 1)
	private int limiteCliente;

	@NotNull
	private byte[] imagemCard;

	@NotNull
	private byte[] imagemBanner;

	private LocalDate transformaData() {
		String[] dataEvento = data.split("-");
		int dia = Integer.parseInt(dataEvento[2]);
		int mes = Integer.parseInt(dataEvento[1]);
		int ano = Integer.parseInt(dataEvento[0]);
		LocalDate dtEvento = LocalDate.of(ano, mes, dia);
		return dtEvento;
	}

	public Evento transforma() {
		Evento evento = new Evento();
		evento.setCasaDeShow(casaDeShow);
		evento.setData(transformaData());
		evento.setDescricao(descricao);
		evento.setImagemCard(imagemCard);
		evento.setImagemBanner(imagemBanner);
		evento.setIngressosDisponiveis(ingressosDisponiveis);
		evento.setLimiteCliente(limiteCliente);
		evento.setNome(nome);
		evento.setPequenaDescricao(pequenaDescricao);
		evento.setPreco(preco);
		return evento;
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

	public String getPequenaDescricao() {
		return pequenaDescricao;
	}

	public void setPequenaDescricao(String pequenaDescricao) {
		this.pequenaDescricao = pequenaDescricao;
	}

	public String getCasaDeShow() {
		return casaDeShow;
	}

	public void setCasaDeShow(String casaDeShow) {
		this.casaDeShow = casaDeShow;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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

	public byte[] getImagemBanner() {
		return imagemBanner;
	}

	public void setImagemBanner(byte[] imagemBanner) {
		this.imagemBanner = imagemBanner;
	}

}
