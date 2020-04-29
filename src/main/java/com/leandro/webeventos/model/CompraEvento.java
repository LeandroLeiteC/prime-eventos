package com.leandro.webeventos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "compra_evento")
@Getter
@Setter
public class CompraEvento extends EntidadeBase {

	@ManyToOne
	@JoinColumn(name = "evento_id", nullable = false)
	private Evento evento;

	@Column(name = "quantidade_de_ingresso", nullable = false)
	private int qtdIngresso = 1;

	@ManyToOne
	@JoinColumn(name = "compra_id", nullable = false)
	private Compra compra;

	@Column(name = "valor_hora_compra")
	
	private double valorUnitario;

	@Column(name = "subtotal", nullable = false)
	private double subtotal;

	public CompraEvento() {
		super();
	}

	public CompraEvento(Long id) {
		super.setId(id);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorUnitario);
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
		CompraEvento other = (CompraEvento) obj;
		if (evento == null) {
			if (other.evento != null)
				return false;
		} else if (!evento.equals(other.evento))
			return false;
		if (Double.doubleToLongBits(valorUnitario) != Double.doubleToLongBits(other.valorUnitario))
			return false;
		return true;
	}


}
