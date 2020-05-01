package com.leandro.webeventos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leandro.webeventos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("select u from Usuario u where u.email like :email")
	Optional<Usuario> findByEmail(@Param("email") String email);

	@Query("select u from Usuario u where u.email like :email and u.ativo = true")
	Optional<Usuario> findByEmailEAtivo(String email);
}
