package com.leandro.webeventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leandro.webeventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

	@Query(value = " SELECT * FROM evento WHERE ingressos_disponiveis > 0 ORDER BY data LIMIT 3", nativeQuery = true)
	List<Evento> findFirst3();
	
	@Query("select e from Evento e where e.ingressosDisponiveis > 0")
	List<Evento> findTodos();
}
