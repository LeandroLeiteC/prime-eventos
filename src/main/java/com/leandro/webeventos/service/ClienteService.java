package com.leandro.webeventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.PerfilTipo;
import com.leandro.webeventos.model.Usuario;
import com.leandro.webeventos.repository.ClienteRepository;

@Service
public class ClienteService {

	private ClienteRepository repository;
	private UsuarioService service;

	@Autowired
	public ClienteService(ClienteRepository repository, UsuarioService service) {
		this.repository = repository;
		this.service = service;
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
	public Cliente buscarPorUsuario(String email) {
		Usuario usuario = service.buscarPorEmail(email).get();
		Cliente cliente = repository.findByUsuario(usuario);
		return cliente;
	}

	@Transactional(readOnly = false)
	public void cadastrarNovoUsuario(Cliente cliente) {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getUsuario().getPassword());
		cliente.getUsuario().setPassword(crypt);
		cliente.getUsuario().addPerfil(PerfilTipo.CLIENTE);
		cliente.getUsuario().setAtivo(true);
		repository.save(cliente);
	}

	@Transactional(readOnly = false)
	public void cadastrarNovoAdmin(Cliente cliente) {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getUsuario().getPassword());
		cliente.getUsuario().setPassword(crypt);
		cliente.getUsuario().addPerfil(PerfilTipo.ADMIN);
		cliente.getUsuario().setAtivo(true);
		repository.save(cliente);
	}
}
