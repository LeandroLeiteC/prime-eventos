package com.leandro.webeventos.controller.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leandro.webeventos.controller.dto.CasaShowDTO;
import com.leandro.webeventos.service.CasaShowService;

@Controller
@RequestMapping("/admin")
public class AdminCasaDeShowController {

	private CasaShowService service;
	
	public AdminCasaDeShowController(CasaShowService service) {
		this.service = service;
	}
	
	@GetMapping("cadastro/casa-de-show")
	public String formCasaShow(ModelMap model) {
		model.addAttribute("casaShow", new CasaShowDTO());
		return "administracao/cadastroCasaDeShow";
	}
	
	@PostMapping("cadastro/cadastrar-casa-de-show")
	public String cadastrarCasaShow(@Valid @ModelAttribute("casaShow") CasaShowDTO casaShow,
									BindingResult result,
									RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "administracao/cadastroCasaDeShow";
		}else {
			service.salvar(casaShow);
			
			attr.addFlashAttribute("alerta", "sucesso");
			attr.addFlashAttribute("texto", "Casa de show Cadastrada!");
			return "redirect:/admin/cadastro/casa-de-show";
		}
		
	}
}
