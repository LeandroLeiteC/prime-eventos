package com.leandro.webeventos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leandro.webeventos.controller.dto.EventoDTO;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.repository.EventoRepository;

@Service
public class EventoService {

	private EventoRepository repository;
	
	public EventoService(EventoRepository repository) {
		this.repository = repository;
	}
	
	
	public void salvarEvento(EventoDTO eventoDTO) {
		Evento evento = eventoDTO.transforma();
		repository.save(evento);
	}


	public List<Evento> buscarTodos() {
		return repository.findAll();
	}
}
