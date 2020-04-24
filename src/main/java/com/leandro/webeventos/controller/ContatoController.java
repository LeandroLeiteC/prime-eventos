package com.leandro.webeventos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contato")
public class ContatoController {
	
	@GetMapping("")
	public String contato() {
		return "contato/contato";
	}

	
}
