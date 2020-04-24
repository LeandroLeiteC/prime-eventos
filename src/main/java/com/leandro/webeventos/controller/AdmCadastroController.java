package com.leandro.webeventos.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.leandro.webeventos.controller.dto.EventoDTO;
import com.leandro.webeventos.model.CasaShow;
import com.leandro.webeventos.service.EventoService;

@Controller
@RequestMapping("admin")
public class AdmCadastroController {

	private EventoService service;
	
	@Autowired
	public AdmCadastroController(EventoService service) {
		this.service = service;
	}
	
	@GetMapping("cadastro/evento")
	public String cadastros(Model model) {
		List<CasaShow> casasDeShow = 
		model.addAttribute("eventoDTO", new EventoDTO());
		return "administracao/cadastroEvento";
	}

	@PostMapping("/cadastrarEvento")
	public String cadastrarEvento(@Valid @ModelAttribute("eventoDTO") EventoDTO eventoDTO, BindingResult result, ModelMap model,
			@RequestParam(required = false, value = "imagemCard") MultipartFile imagemCard,
			@RequestParam(required = false, value = "imagemBanner") MultipartFile imagemBanner) {
		byte[] bImagem;
		
		if(result.hasErrors()){
			return "administracao/cadastros";
			
		}else if(!imagemCard.isEmpty()) {
			try {
				bImagem = imagemCard.getBytes();
				eventoDTO.setImagemCard(bImagem);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		service.salvarEvento(eventoDTO);
		
		return "redirect:/admin/cadastro";
	}

	@PostMapping("/cadastrarCasaDeShow")
	public String cadastrarCasaShow(@ModelAttribute CasaShow casaShow) {
		System.out.println(casaShow);
		return "redirect:/admin/cadastro";
	}
}
