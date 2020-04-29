package com.leandro.webeventos.controller.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leandro.webeventos.controller.dto.ClienteDTO;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.service.ClienteService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	private ClienteService service;
	
	public AdminController(ClienteService service) {
		this.service = service;
	}

	@GetMapping("cadastro/novo-admin")
	public String cadastrarNovoAdmin(ModelMap model) {
		model.addAttribute("clienteDTO", new ClienteDTO());
		return "administracao/cadastro-admin";
	}
	
	@PostMapping("cadastro/novo-admin")
	public String processarCadastro(@Valid @ModelAttribute("clienteDTO") ClienteDTO clienteDTO,
				BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "administracao/cadastro-admin";
		} else if (service.emailJaExiste(clienteDTO.getUsuario().getEmail())) {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email já cadastrado.");
			return "administracao/cadastro-admin";
		} else if (clienteDTO.getUsuario().equalPassword()) {

			Cliente cliente = clienteDTO.transforma();
			service.cadastrarNovoAdmin(cliente);

			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Conta cadastrada! Já pode fazer login!");
			return "administracao/cadastro-admin";
		} else {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Senhas não são iguais.");
			return "administracao/cadastro-admin";
		}
		
	}
}
