package com.leandro.webeventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.webeventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

}
