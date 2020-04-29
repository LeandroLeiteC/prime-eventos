package com.leandro.webeventos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.leandro.webeventos.service.UsuarioService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
private UsuarioService service;
	
	@Autowired
	public WebSecurityConfig(UsuarioService service) {
		this.service = service;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()
				.antMatchers("/", "/eventos/**", "/novo-cadastro").permitAll()
				.antMatchers("/cadastro", "/cadastrarCliente").permitAll()
				
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/process-login").permitAll()
				.defaultSuccessUrl("/", true)
			.and()
				.logout()
				.logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}	
}
