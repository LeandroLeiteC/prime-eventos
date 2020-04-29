package com.leandro.webeventos.controller.dto;

import java.sql.Date;
import java.time.LocalTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.leandro.webeventos.model.CasaShow;
import com.leandro.webeventos.model.Evento;

public class EventoDTO {

	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	@Size(min = 5)
	private String descricao;

	@NotBlank
	@Size(min = 5, max = 100)
	private String pequenaDescricao;

	@NotNull
	private Long casaDeShow;

	@NotNull
	private Date data;

	@NotBlank
	private String preco;

	@NotNull
	@Min(value = 1)
	private int ingressosDisponiveis;

	@NotNull
	@Min(value = 1)
	private int limiteCliente;

	@NotNull
	private LocalTime hora;

	private byte[] imagemCard;

	private byte[] imagemBanner;

	private CasaShow casaShow;

	public Evento transforma() {
		Evento evento = new Evento();

		if (id != null) {
			evento.setId(id);
		}
		
		evento.setHora(hora);
		evento.setCasaDeShow(casaShow);
		evento.setData(data);
		evento.setDescricao(descricao);
		evento.setImagemCard(imagemCard);
		evento.setImagemBanner(imagemBanner);
		evento.setIngressosDisponiveis(ingressosDisponiveis);
		evento.setLimiteCliente(limiteCliente);
		evento.setNome(nome);
		evento.setPequenaDescricao(pequenaDescricao);
		double p = Double.parseDouble(preco.replace(",", "."));
		evento.setPreco(p);
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

	public Long getCasaDeShow() {
		return casaDeShow;
	}

	public void setCasaDeShow(Long casaDeShow) {
		this.casaDeShow = casaDeShow;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
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

	public void setImagemBanner(byte[] imagembanner) {
		this.imagemBanner = imagembanner;
	}

	public CasaShow getCasaShow() {
		return casaShow;
	}

	public void setCasaShow(CasaShow casaShow) {
		this.casaShow = casaShow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

}
