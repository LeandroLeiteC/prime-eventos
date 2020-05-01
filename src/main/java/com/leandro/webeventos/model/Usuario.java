package com.leandro.webeventos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario", indexes = { @Index(name = "idx_usuario_email", columnList = "email") })
@Getter
@Setter
public class Usuario extends EntidadeBase {

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@JsonIgnore
	@Column(name = "senha", nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(name = "usuarios_tem_perfis", joinColumns = {
			@JoinColumn(name = "usuario_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "perfil_id", referencedColumnName = "id") })
	private List<Perfil> perfis;

	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	@Column(name = "codigo", nullable = true)
	private String codigo;

	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		super.setId(id);
	}

	public void addPerfil(PerfilTipo tipo) {
		if (this.perfis == null) {
			this.perfis = new ArrayList<Perfil>();
		}
		this.perfis.add(new Perfil(tipo.getCod()));
	}

}
