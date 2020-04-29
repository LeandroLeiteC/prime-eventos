package com.leandro.webeventos.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "compra")
@Getter
@Setter
public class Compra extends EntidadeBase {

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@ManyToMany(mappedBy = "compra", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Set<CompraEvento> compraEventos;

	@Column(name = "hora_da_venda", nullable = false)
	private LocalDateTime hora;

	@Column(name = "total")
	private double total;

	public Compra() {
		super();
	}

	public Compra(Long id) {
		super.setId(id);
	}

	public double calcularTotal() {
		double t = 0;
		for(CompraEvento ce : compraEventos) {
			t += ce.getSubtotal();
		}
		return t;
	}
}
