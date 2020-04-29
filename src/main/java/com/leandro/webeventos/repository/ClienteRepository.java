package com.leandro.webeventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select c from Cliente c where c.usuario = :usuario")
	Cliente findByUsuario(Usuario usuario);

}
