package com.leandro.webeventos.controller.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("login")
	public String login(@RequestParam(required = false, name = "error") String error, ModelMap model) {
		if(error != null) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email ou senha inválidos.");
		}
		return "login";
	}

	@GetMapping("cadastro")
	public String cadastro(ModelMap model) {
		model.addAttribute("clienteDTO", new ClienteDTO());
		return "cadastro/new-cadastro";
	}

	@PostMapping("cadastro")
	public String cadastrarCliente(@Valid @ModelAttribute("clienteDTO") ClienteDTO clienteDTO, BindingResult result,
			ModelMap model) {
		if (result.hasErrors()) {
			return "cadastro/new-cadastro";
		} else if (service.emailJaExiste(clienteDTO.getUsuario().getEmail())) {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email já cadastrado.");
			return "cadastro/new-cadastro";
		} else if (clienteDTO.getUsuario().equalPassword()) {

			Cliente cliente = clienteDTO.transforma();
			service.cadastrarNovoUsuario(cliente);

			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Conta cadastrada! Já pode fazer login!");
			return "cadastro/new-cadastro";
		} else {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Senhas não são iguais.");
			return "cadastro/new-cadastro";
		}
	}
	
	@GetMapping("/accesso-negado")
	public String acessDeniedController(ModelMap model) {
		model.addAttribute("title", "Acesso Negado");
		return "accesso-negado";
	}
	
}
