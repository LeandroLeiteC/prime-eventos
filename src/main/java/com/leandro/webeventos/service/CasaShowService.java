package com.leandro.webeventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.webeventos.controller.dto.CasaShowDTO;
import com.leandro.webeventos.model.CasaShow;
import com.leandro.webeventos.repository.CasaShowRepository;

import java.util.List;

@Service
public class CasaShowService {

	private CasaShowRepository repository;

	@Autowired
	public CasaShowService(CasaShowRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = false)
	public void salvar(CasaShowDTO casaShowDTO) {
		CasaShow casaShow = casaShowDTO.transforma();
		repository.save(casaShow);
	}
	
	@Transactional(readOnly = true)
	public List<CasaShow> buscarTodos(){
		return repository.findAllByOrderByNomeASC();
	}
	
	@Transactional(readOnly = true)
	public CasaShow buscarPeloId(Long id) {
		return repository.findById(id).get();
	}
}
