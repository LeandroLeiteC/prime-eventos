package com.leandro.webeventos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FotosController {

	@PostMapping("/empresas/foto")
	public String upload(MultipartFile[] files) {
		return "Ok";
	}
	
}
