package com.leandro.webeventos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.leandro.webeventos.controller.dto.ClienteDTO;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.service.ClienteService;

@Controller
public class LoginController {

	private ClienteService service;
	
	@Autowired
	public LoginController(ClienteService service) {
		this.service = service;
	}
	
	@GetMapping("")
	public String hello() {
		return "fragments/index/conteudo";
	}
	
	@GetMapping("login")
	public String login() {
		return "fragments/index/conteudo";
	}
	
	@GetMapping("cadastro")
	public String cadastro(ModelMap model) {
		model.addAttribute("clienteDTO", new ClienteDTO());
		return "cadastro/cadastro";
	}
	
	@PostMapping("cadastrarCliente")
	public String cadastrarCliente(@Valid @ModelAttribute("clienteDTO")  ClienteDTO clienteDTO,
									BindingResult result,
									ModelMap model
									) {
		System.out.println(result.hasFieldErrors());
		if(result.hasErrors()) {
			System.out.println(clienteDTO.getNascimento());
			return "cadastro/cadastro";
		}else if(service.emailJaExiste(clienteDTO.getUsuario().getEmail())) {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email já cadastrado.");
			return "cadastro/cadastro";
		}
		else if(clienteDTO.getUsuario().equalPassword()) {
			
			Cliente cliente = clienteDTO.transforma();
			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Conta cadastrada! Já pode fazer login!");
			service.cadastrarNovoUsuario(cliente);
			return "cadastro/cadastro";
		}else {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Senhas não são iguais.");
			return "cadastro/cadastro";
		}
	}
	
}
