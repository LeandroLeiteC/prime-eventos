package com.leandro.webeventos.model;

import java.sql.Date;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leandro.webeventos.controller.dto.EventoDTO;
import com.leandro.webeventos.controller.encoder.Encode64;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "evento")
@Getter
@Setter
public class Evento extends EntidadeBase {

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "pequena_descricao", nullable = false)
	private String pequenaDescricao;

	@ManyToOne
	@JoinColumn(name = "casa_de_show")
	private CasaShow casaDeShow;

	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "hora", nullable = true)
	private LocalTime hora;

	@Column(name = "preco", nullable = false)
	private double preco;

	@Column(name = "ingressos_disponiveis", nullable = false)
	private int ingressosDisponiveis;

	@Column(name = "limite_ingressos_por_cliente", nullable = false)
	private int limiteCliente;

	@Column(name = "imagemCard")
	private byte[] imagemCard;

	@Column(name = "imagemBanner")
	private byte[] imagemBanner;

	@Transient
	private String imagemCard64;

	@Transient
	private String imagemBanner64;

	@Transient
	private String dataString;

	public Evento() {
		super();
	}

	public Evento(Long id) {
		super.setId(id);
	}

	public void transformaDados() {
		String[] dt = this.data.toString().split("-");
		String dtConcat = dt[2] + "/" + dt[1] + "/" + dt[0];
		this.dataString = dtConcat;
		imagemCard64 = Encode64.encode64(imagemCard);
		imagemBanner64 = Encode64.encode64(imagemBanner);
	}

	public EventoDTO transforma() {
		EventoDTO eventoDTO = new EventoDTO();
		eventoDTO.setCasaDeShow(casaDeShow.getId());
		eventoDTO.setData(data);
		eventoDTO.setHora(hora);
		eventoDTO.setDescricao(descricao);
		eventoDTO.setId(getId());
		eventoDTO.setIngressosDisponiveis(ingressosDisponiveis);
		eventoDTO.setLimiteCliente(limiteCliente);
		eventoDTO.setNome(nome);
		eventoDTO.setPequenaDescricao(pequenaDescricao);
		eventoDTO.setPreco(String.valueOf(preco).replace(".", ","));
		eventoDTO.setImagemCard(imagemCard);
		eventoDTO.setImagemBanner(imagemBanner);
		return eventoDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(preco);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Double.doubleToLongBits(preco) != Double.doubleToLongBits(other.preco))
			return false;
		return true;
	}

}
