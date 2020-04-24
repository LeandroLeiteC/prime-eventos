package com.leandro.webeventos.model;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), CLIENTE(2, "CLIENTE");
	
	private long cod;
	private String descricao;
	
	private PerfilTipo(long cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public long getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
