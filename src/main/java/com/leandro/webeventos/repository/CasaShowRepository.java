package com.leandro.webeventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leandro.webeventos.model.CasaShow;

public interface CasaShowRepository extends JpaRepository<CasaShow, Long> {

	@Query("select c from CasaShow c order by c.nome")
	public List<CasaShow> findAllByOrderByNomeASC();
}
