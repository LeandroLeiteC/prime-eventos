package com.leandro.webeventos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.webeventos.controller.dto.EventoDTO;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.repository.EventoRepository;

@Service
public class EventoService {

	private EventoRepository repository;
	
	public EventoService(EventoRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = false)
	public void salvarEvento(EventoDTO eventoDTO) {
		Evento evento = eventoDTO.transforma();
		repository.save(evento);
	}
	
	@Transactional(readOnly = false)
	public void atualizar(Evento evento) {
		repository.save(evento);
	}

	@Transactional(readOnly = true)
	public List<Evento> buscarTodosCliente() {
		return repository.findTodos();
	}
	
	@Transactional(readOnly = true)
	public List<Evento> buscarTodosAdmin() {
		return repository.findAll();
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Evento> buscarTop3(){
		return repository.findFirst3();
	}

	@Transactional(readOnly = true)
	public Evento buscarPorId(Long id) {
		return repository.findById(id).get();
	}
}
