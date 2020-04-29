package com.leandro.webeventos.controller.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Compra;
import com.leandro.webeventos.model.CompraEvento;
import com.leandro.webeventos.service.ClienteService;
import com.leandro.webeventos.service.CompraService;

@Controller
@RequestMapping("cliente")
public class ClienteController {

	private CompraService service;
	private ClienteService clienteService;
	
	@Autowired
	public ClienteController(CompraService service, ClienteService clienteService) {
		this.service = service;
		this.clienteService = clienteService;
	}

	@GetMapping("compras")
	public String mostrarCompras(ModelMap model) {
		Cliente cliente = getCliente();
		List<Compra> compras = service.buscarComprasDoCliente(cliente);
		model.addAttribute("compras", compras);
		for(Compra compra : compras ) {
			for(CompraEvento compraEvento : compra.getCompraEventos()) {
				compraEvento.getEvento().transformaDados();
			}
		}
		return "cliente/compras";
	}
	
	private Cliente getCliente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<Cliente> cliente = clienteService.buscarPorUsuario(email);
		if(cliente.isPresent()) {
			return cliente.get();
		}else {
			return null;
		}
	}
}
