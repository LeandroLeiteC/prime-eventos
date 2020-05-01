package com.leandro.webeventos.controller.controller;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			ModelMap model) throws MessagingException {
		if (result.hasErrors()) {
		} else if (service.emailJaExiste(clienteDTO.getUsuario().getEmail())) {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Email já cadastrado.");
		} else if (clienteDTO.getUsuario().equalPassword()) {

			Cliente cliente = clienteDTO.transforma();
			service.cadastrarNovoCliente(cliente);
			clienteDTO = new ClienteDTO();
			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Um email de confirmação foi enviado!");
		} else {
			model.addAttribute("clienteDTO", clienteDTO);
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "Senhas não são iguais.");
			
		}
		
		return "cadastro/new-cadastro";
	}
	
	@GetMapping("confirmacao/cadastro")
	public String confirmacao(@RequestParam("codigo") String codigo, RedirectAttributes attr) throws AccessDeniedException {
		service.ativarCadastroCliente(codigo);
		attr.addFlashAttribute("alerta", "sucesso");
		attr.addFlashAttribute("texto", "Seu cadastro está ativo!");
		return "redirect:/login";
	}

	@GetMapping("redefinir/senha")
	public String esqueceuSenha(@RequestParam(name = "email", required = false) String email, ModelMap model) throws MessagingException {
		Optional<Usuario> usuario = usuarioService.buscarPorEmailEAtivo(email);
		if(usuario.isPresent()) {
			usuarioService.redefinicaoDeSenha(email);
			model.addAttribute("email", email);
			return "recuperacao/codigo";
		}else if(email != null && !email.isEmpty() ){
			model.addAttribute("alerta", "Email inválido");
			return "recuperacao/recuperar-senha";
		}else {
			return "recuperacao/recuperar-senha";
		}
	}
	
	@GetMapping("redefinir/senha/verificacao")
	public String verificacao(@RequestParam(name = "email") String email,
			@RequestParam(name = "codigo") String codigo, ModelMap model) {
		Usuario usuario = usuarioService.buscarPorEmailEAtivo(email).get();	
		if(!usuario.getCodigo().equals(codigo) || codigo == null) {
			model.addAttribute("email", email);
			model.addAttribute("alerta", "Código de verificação não confere.");
			return "recuperacao/codigo";
		}else {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setEmail(email);
			model.addAttribute("usuarioDTO", usuarioDTO);
			return "recuperacao/nova-senha";
		}
	}
	
	@PostMapping("redefinir/senha/finalizar")
	public String finalizarRedefinicaoSenha(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
			BindingResult result, ModelMap model){
		if(result.hasErrors()) {
			return "recuperarcao/nova-senha";
		}else {
			Usuario usuario = usuarioDTO.transformaUsuario();
			model.addAttribute("alerta", "sucesso");
			model.addAttribute("texto", "Nova senha atualizada!");
			usuarioService.atualizarSenha(usuario);
			return "redirect:/login";
		}
	}
	
}
