package com.leandro.webeventos.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.webeventos.model.Perfil;
import com.leandro.webeventos.model.Usuario;
import com.leandro.webeventos.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	private UsuarioRepository repository;
	private MailService mailService;

	@Autowired
	public UsuarioService(UsuarioRepository repository, MailService mailService) {
		this.repository = repository;
		this.mailService = mailService;
	}

	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmailEAtivo(String email) {
		return repository.findByEmailEAtivo(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = buscarPorEmail(username);
		if (!usuario.isPresent()) {
			throw new BadCredentialsException("Email ou senha inválidos!");
		} else if (!usuario.get().isAtivo()) {
			throw new DisabledException("Sua conta ainda não está ativa!");
		}
		Usuario user = usuario.get();

		return new User(user.getEmail(), user.getPassword(), user.isAtivo(), true, true, true,
				AuthorityUtils.createAuthorityList(getAuthorities(user.getPerfis())));
	}

	private String[] getAuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDescricao();
		}
		return authorities;
	}

	@Transactional(readOnly = false)
	public void atualizarSenha(Usuario usuario) {
		Usuario dbUsuario = repository.findByEmailEAtivo(usuario.getEmail()).get();
		String crypt = new BCryptPasswordEncoder().encode(usuario.getPassword());
		dbUsuario.setPassword(crypt);
		dbUsuario.setCodigo(null);
	}

	@Transactional(readOnly = false)
	public void redefinicaoDeSenha(String email) throws MessagingException {
		Usuario usuario = repository.findByEmailEAtivo(email).get();
		String verificador = RandomStringUtils.randomAlphanumeric(6);
		usuario.setCodigo(verificador);
		mailService.enviarCodigoRedefinirSenha(email, verificador);
	}
}
