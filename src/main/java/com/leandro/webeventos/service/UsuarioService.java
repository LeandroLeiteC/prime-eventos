package com.leandro.webeventos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = buscarPorEmail(username);
		if(usuario.isPresent()) {
			Usuario user = usuario.get();
			return new User(user.getEmail(),
					user.getPassword(),
					AuthorityUtils.createAuthorityList(getAuthorities(user.getPerfis())));
		}
		throw new UsernameNotFoundException("Usuario não encontrado");
		
	}
	
	private String[] getAuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for(int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDescricao();
		}
		return authorities;
	}
	
	@Transactional(readOnly = false)
	public void atualizarSenha(Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setPassword(crypt);
		repository.save(usuario);
	}
}
