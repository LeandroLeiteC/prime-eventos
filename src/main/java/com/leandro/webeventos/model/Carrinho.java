package com.leandro.webeventos.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@Component
@SessionScope
@Getter
@Setter
public class Carrinho {

	private Set<CompraEvento> compraEventos = new HashSet<>();
	private int numero = 0;
	
	public int quantidade() {
		return compraEventos.size();
	}

	public void addCompraEvento(CompraEvento compraEvento) {
		compraEventos.add(compraEvento);
	}

	public void removeCompraEvento(CompraEvento compraEvento) {
		compraEventos.remove(compraEvento);
	}
	
	public double calcularTotal() {
		double total = 0;
		for(CompraEvento ce : compraEventos) {
			total += ce.getSubtotal();
		}
		return total;
	}
}
