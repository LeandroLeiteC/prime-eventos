package com.leandro.webeventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.webeventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

	List<Evento> findTop3ByOrderByDataAsc();
}
