package com.leandro.webeventos.controller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.leandro.webeventos.model.Carrinho;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.service.EventoService;

@Controller
@SessionAttributes(names = {"carrinho"})
public class HomeController {

	private EventoService service;
	private Carrinho carrinho;
	
	@Autowired
	public HomeController(EventoService service, Carrinho carrinho) {
		this.service = service;
		this.carrinho = carrinho;
	}

	@GetMapping("")
	public String principal(ModelMap model) {
		List<Evento> eventos = service.buscarTop3();
		for(Evento e : eventos) {
			e.transformaDados();
		}
		model.addAttribute("eventos", eventos);
		return "fragments/index/conteudo";
	}
	
	
	@ModelAttribute("carrinho")
	private Carrinho getCarrinho() {
		return carrinho;
	}
	
}
