package com.leandro.webeventos.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.leandro.webeventos.model.Cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

	@NotBlank
	private String nome;

	@Valid
	private UsuarioDTO usuario;

	public Cliente transforma() {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setUsuario(usuario.transformaUsuario());
		return cliente;
	}

}
