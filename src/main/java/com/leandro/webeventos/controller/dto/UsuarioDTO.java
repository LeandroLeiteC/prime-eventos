package com.leandro.webeventos.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.leandro.webeventos.model.Usuario;

public class UsuarioDTO {

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String confirmPassword;

	public Usuario transformaUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
		usuario.setPassword(this.password);
		return usuario;
	}

	public boolean equalPassword() {
		if (password.equals(confirmPassword)) {
			return true;
		} else {
			return false;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [email=" + email + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}

}
