package com.leandro.webeventos.controller.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leandro.webeventos.controller.dto.EventoDTO;
import com.leandro.webeventos.controller.encoder.Encode64;
import com.leandro.webeventos.model.CasaShow;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.service.CasaShowService;
import com.leandro.webeventos.service.EventoService;

@Controller
@RequestMapping("admin")
public class AdminEventoController {

	private EventoService service;
	private CasaShowService csService;
	
	@Autowired
	public AdminEventoController(EventoService service, CasaShowService csService) {
		this.service = service;
		this.csService = csService;
	}
	
	@GetMapping({"cadastro/evento"})
	public String formEvento(ModelMap model) {
		List<CasaShow> casasDeShow = csService.buscarTodos();

		model.addAttribute("titulo", "Cadastro de Evento");
		model.addAttribute("evento", new EventoDTO());
		model.addAttribute("casasDeShow", casasDeShow);
		return "administracao/cadastroEvento";
	}

	@PostMapping({"cadastro/cadastrar-evento", "/atualizar-evento"})
	public String cadastrarEvento(ModelMap model,
			@Valid @ModelAttribute("evento") EventoDTO evento,
			BindingResult result,
			@RequestParam(required = false, value = "imagemC") MultipartFile imagemCard,
			@RequestParam(required = false, value = "imagemB") MultipartFile imagemBanner,
			RedirectAttributes attr) throws IOException {
		
		if(evento.getId() != null) {
			model.addAttribute("titulo", "Atualizar Evento");
		}else {
			model.addAttribute("titulo", "Cadastrar Evento");
		}
		
		byte[] bImagemCard;
		byte[] bImagemBanner = null;
		List<CasaShow> casasDeShow = csService.buscarTodos();
		model.addAttribute("casasDeShow", casasDeShow);
		model.addAttribute("imagemCard", imagemCard.getBytes());
		model.addAttribute("imagemBanner", imagemBanner);
		model.addAttribute("imagemCard64", Encode64.encode64(evento.getImagemCard()));
		model.addAttribute("imagemBanner64", Encode64.encode64(evento.getImagemBanner()));
		
		if(result.hasErrors()){
			System.out.println(result.getAllErrors());
			model.addAttribute("casasDeShow", casasDeShow);
			model.addAttribute("imagemC", imagemCard);
			return "administracao/cadastroEvento";
		}
		
		CasaShow casaShow = csService.buscarPeloId(evento.getCasaDeShow());
		if(evento.getIngressosDisponiveis() > casaShow.getLimitePessoas()) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "O número total de ingressos deve ser menor ou igual"
					+ " a " + casaShow.getLimitePessoas());
			return "administracao/cadastroEvento";
		}
		
		if(evento.getIngressosDisponiveis() < evento.getLimiteCliente()) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("texto", "O número de ingressos por cliente deve ser menor ou igual"
					+ " a " + evento.getIngressosDisponiveis());
			return "administracao/cadastroEvento";
		}
		
		if(imagemCard.getSize() > 0) {
			if(imagemCard.getSize() <= 1048576) {
				bImagemCard = imagemCard.getBytes();
				evento.setImagemCard(bImagemCard);
			}else {
				model.addAttribute("alertaCard", "erro");
				model.addAttribute("textoCard", "Tamanho da imagem excedeu o limite");
				return "administracao/cadastroEvento";
			}
		}else {
			
			if(evento.getId() == null) {
				model.addAttribute("alertaCard", "erro");
				model.addAttribute("textoCard", "É necessário imagem para o Card");
				return "administracao/cadastroEvento";
			}	
		}
				
		if(imagemBanner.getSize() > 0) {
			if(imagemBanner.getSize() <= 1048576) {
				bImagemBanner = imagemBanner.getBytes();
				evento.setImagemBanner(bImagemBanner);
			}else {
				model.addAttribute("alertaBanner", "erro");
				model.addAttribute("textoBanner", "Tamanho da imagem excedeu o limite");
				return "administracao/cadastroEvento";
			}
		}else {
			if(evento.getId() == null) {
				model.addAttribute("alertaBanner", "erro");
				model.addAttribute("textoBanner", "É necessário imagem para o Banner");
				return "administracao/cadastroEvento";
			}
		}
		
		
		
		evento.setCasaShow(casaShow);
		service.salvarEvento(evento);
		attr.addFlashAttribute("alerta", "sucesso");
		if(evento.getId() == null) {
			attr.addFlashAttribute("texto", "Evento Cadastrado!");
		}else {
			attr.addFlashAttribute("texto", "Evento Atualizado!");
			attr.addFlashAttribute("titulo", "Atualizar Evento");
		}
		
		return "redirect:/admin/cadastro/evento";
	}
	
	@GetMapping("/atualizar/{id}")
	public String formAtualizar(@PathVariable("id") Long id, ModelMap model) {
		Evento evento = service.buscarPorId(id);
		EventoDTO eventoDTO = evento.transforma();
		List<CasaShow> casasDeShow = csService.buscarTodos();
		
		model.addAttribute("imagemCard64", Encode64.encode64(evento.getImagemCard()));
		model.addAttribute("imagemBanner64", Encode64.encode64(evento.getImagemBanner()));
		model.addAttribute("casasDeShow", casasDeShow);
		model.addAttribute("evento", eventoDTO);
		model.addAttribute("titulo", "Atualizar Evento");
		return "administracao/cadastroEvento";
	}
	
	@GetMapping("eventos")
	public String todosEventos(ModelMap model) {
		List<Evento> eventos = service.buscarTodosAdmin();
		for(Evento e : eventos) {
			e.transformaDados();
		}
		model.addAttribute("eventos", eventos);
		return "eventos/eventos";
	}
}
