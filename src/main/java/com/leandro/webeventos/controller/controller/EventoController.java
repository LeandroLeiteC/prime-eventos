package com.leandro.webeventos.controller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.service.EventoService;

@Controller
@RequestMapping("eventos")
public class EventoController {

	private EventoService service;
	
	@Autowired
	public EventoController(EventoService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public String eventos(ModelMap model) {
		List<Evento> eventos = service.buscarTodosCliente();
		for(Evento e : eventos) {
			e.transformaDados();
		}
		
		
		model.addAttribute("eventos", eventos);
		return "eventos/eventos";
	}

	@GetMapping("{id}")
	public String detalhes(@PathVariable("id") Long id, ModelMap model) {
		Evento evento = service.buscarPorId(id);
		evento.transformaDados();
		model.addAttribute("evento", evento);
		return "eventos/detalhes";
	}
}
