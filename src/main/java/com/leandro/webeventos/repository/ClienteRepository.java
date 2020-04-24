package com.leandro.webeventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.webeventos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
