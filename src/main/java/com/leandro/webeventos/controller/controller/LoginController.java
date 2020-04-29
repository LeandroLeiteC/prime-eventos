package com.leandro.webeventos.controller.controller;

import java.util.Optional;

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
import com.leandro.webeventos.controller.dto.UsuarioDTO;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Usuario;
import com.leandro.webeventos.service.ClienteService;
import com.leandro.webeventos.service.UsuarioService;

@Controller
public class LoginController {

	private ClienteService service;
	private UsuarioService usuarioService;

	@Autowired
	public LoginController(ClienteService service, UsuarioService usuarioService) {
		this.service = service;
		this.usuarioService = usuarioService;
	}

	@GetMapping("login")
	public String login(@RequestParam(required = false, name = "error") String error, ModelMap model) {
		if (error != null) {
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

	@GetMapping("accesso-negado")
	public String acessDeniedController(ModelMap model) {
		model.addAttribute("title", "Acesso Negado");
		return "accesso-negado";
	}

	@GetMapping("esqueceu")
	public String esqueceuSenha(ModelMap model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "esqueceu";
	}

	@PostMapping("esqueceu")
	public String processEsqueceu(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "esqueceu";
		}
		
		Optional<Usuario> user = usuarioService.buscarPorEmail(usuarioDTO.getEmail());
		
		if(!user.isPresent()) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email não encontrado");
		}else if(!usuarioDTO.equalPassword()) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Senhas não são iguais");
		}else {
			Usuario usuario = user.get();
			usuario.setPassword(usuarioDTO.getPassword());
			usuarioService.atualizarSenha(usuario);
			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Senha atualizada!");
		}

		return "esqueceu";
	}
}
