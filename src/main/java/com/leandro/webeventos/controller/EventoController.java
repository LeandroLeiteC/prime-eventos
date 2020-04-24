package com.leandro.webeventos.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
		List<Evento> eventos = service.buscarTodos();
		byte[] encodeBase64;
		String baseEncode64;
		
		for(Evento e : eventos) {
			encodeBase64 = Base64.getEncoder().encode(e.getImagemCard());
			try {
				baseEncode64 = new String(encodeBase64, "UTF-8");
				e.setImagemCard64(baseEncode64);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		model.addAttribute("eventos", eventos);
		return "eventos/eventos";
	}
}
