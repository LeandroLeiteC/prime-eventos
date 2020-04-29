package com.leandro.webeventos.controller.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.leandro.webeventos.model.Carrinho;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.service.ClienteService;
import com.leandro.webeventos.service.EventoService;

@Controller
@SessionAttributes(names = {"carrinho", "nome"})
public class HomeController {

	private EventoService service;
	private Carrinho carrinho;
	private ClienteService clienteService;
	
	@Autowired
	public HomeController(EventoService service, Carrinho carrinho, ClienteService clienteService) {
		this.service = service;
		this.carrinho = carrinho;
		this.clienteService = clienteService;
	}

	@GetMapping("/")
	public String principal(ModelMap model) {
		List<Evento> eventos = service.buscarTop3();
		for(Evento e : eventos) {
			e.transformaDados();
		}
		model.addAttribute("eventos", eventos);
		return "/fragments/index/conteudo";
	}
	
	
	@ModelAttribute("carrinho")
	private Carrinho getCarrinho() {
		return carrinho;
	}
	
	@ModelAttribute("nome")
	private String getCliente() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String email = authentication.getName();
			Optional<Cliente> cliente = clienteService.buscarPorUsuario(email);
			if(cliente.isPresent()) {
				return cliente.get().getNome();
			}else {
				return null;
			}
		}catch (NoSuchElementException e) {
			return null;
		}
		
	}
	
}
