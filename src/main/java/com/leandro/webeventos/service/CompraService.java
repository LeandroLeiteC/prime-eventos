package com.leandro.webeventos.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.leandro.webeventos.model.Carrinho;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Compra;
import com.leandro.webeventos.model.CompraEvento;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.repository.CompraRepository;

@Service
@SessionAttributes(names = "carrinho")
public class CompraService {

	private CompraRepository repository;
	private EventoService eventoService;
	private Carrinho carrinho;
	
	@Autowired
	public CompraService(CompraRepository repository, EventoService eventoService, Carrinho carrinho) {
		this.repository = repository;
		this.eventoService = eventoService;
		this.carrinho = carrinho;
	}
	
	@Transactional(readOnly = false)
	public void salvar(Compra compra) {
		for(CompraEvento ce : compra.getCompraEventos()) {
			int ingressos = ce.getEvento().getIngressosDisponiveis() - ce.getQtdIngresso();
			Evento evento = eventoService.buscarPorId(ce.getEvento().getId());
			evento.setIngressosDisponiveis(ingressos);
			eventoService.atualizar(evento);
			carrinho.setCompraEventos(new HashSet<>());
		}
		repository.save(compra);
	}
	
	@Transactional(readOnly = true)
	public List<Compra> buscarTodas() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public int podeComprar(@SessionAttribute(name = "cliente") Cliente cliente, Evento evento) {
		int limite = evento.getLimiteCliente();
		int comprado = contarIngresso(cliente, evento);
		
		if(evento.getIngressosDisponiveis() < (limite - comprado)) {
			return evento.getIngressosDisponiveis();
		}else if(comprado < limite) {
			return (limite - comprado);
		}
		
		return 0;
	}

	@Transactional(readOnly = true)
	private int contarIngresso(Cliente cliente, Evento evento) {
		int comprado = 0;
		for(Compra compra : cliente.getCompras()) {
			for(CompraEvento compraEvento : compra.getCompraEventos()) {
				if(compraEvento.getEvento().getId() == evento.getId()) {
					comprado += compraEvento.getQtdIngresso();
					break;
				}
			}
		}
		return comprado;
	}
}
