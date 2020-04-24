package com.leandro.webeventos.controller.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.leandro.webeventos.model.Cliente;

public class ClienteDTO {
	

	@NotBlank
	private String nome;
	
	@NotBlank
	private String nascimento;
	
	@Valid
	private UsuarioDTO usuario;

	public Cliente transforma() {
		String[] data = nascimento.split("-");
		int dia = Integer.parseInt(data[2]);
		int mes = Integer.parseInt(data[1]);
		int ano = Integer.parseInt(data[0]);
		LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setNascimento(dataNascimento);
		cliente.setUsuario(usuario.transformaUsuario());
		return cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "ClienteDTO [nome=" + nome + ", nascimento=" + nascimento + ", usuario=" + usuario + "]";
	}

}
