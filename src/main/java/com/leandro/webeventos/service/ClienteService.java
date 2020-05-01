package com.leandro.webeventos.service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.PerfilTipo;
import com.leandro.webeventos.model.Usuario;
import com.leandro.webeventos.repository.ClienteRepository;

@Service
public class ClienteService {

	private ClienteRepository repository;
	private UsuarioService service;
	private MailService mailService;

	@Autowired
	public ClienteService(ClienteRepository repository, UsuarioService service, MailService mailService) {
		this.repository = repository;
		this.service = service;
		this.mailService = mailService;
	}

	@Transactional(readOnly = true)
	public boolean emailJaExiste(String email) {
		if (service.buscarPorEmail(email).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Optional<Cliente> buscarPorUsuario(String email) {
		Usuario usuario = service.buscarPorEmail(email).get();
		Optional<Cliente> cliente = repository.findByUsuario(usuario);
		return cliente;
	}

	@Transactional(readOnly = false)
	public void cadastrarNovoCliente(Cliente cliente) throws MessagingException {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getUsuario().getPassword());
		cliente.getUsuario().setPassword(crypt);
		cliente.getUsuario().addPerfil(PerfilTipo.CLIENTE);
		cliente.getUsuario().setAtivo(false);
		repository.save(cliente);
		
		emailConfirmacaoCadastro(cliente.getUsuario().getEmail());
	}

	@Transactional(readOnly = false)
	public void cadastrarNovoAdmin(Cliente cliente) {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getUsuario().getPassword());
		cliente.getUsuario().setPassword(crypt);
		cliente.getUsuario().addPerfil(PerfilTipo.ADMIN);
		cliente.getUsuario().setAtivo(false);
		repository.save(cliente);
	}

	public void emailConfirmacaoCadastro(String email) throws MessagingException {
		String codigo = Base64Utils.encodeToString(email.getBytes());
		mailService.enviarConfirmacaoCadastro(email, codigo);
	}
	
	@Transactional(readOnly = false)
	public void ativarCadastroCliente(String codigo) throws AccessDeniedException {
		String email = new String(Base64Utils.decodeFromString(codigo));
		Optional<Cliente> cli = buscarPorUsuario(email);
		
		if(cli.isPresent()) {
			Cliente cliente = cli.get();
			if(cliente.getId() == null) {
				throw new AccessDeniedException("Não foi possível ativar seu cadastro");
			}
			cliente.getUsuario().setAtivo(true);
		}else {
			throw new AccessDeniedException("Não foi possível ativar seu cadastro");
		}
	}
	
}
