package com.leandro.webeventos.controller.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leandro.webeventos.model.Carrinho;
import com.leandro.webeventos.model.Cliente;
import com.leandro.webeventos.model.Compra;
import com.leandro.webeventos.model.CompraEvento;
import com.leandro.webeventos.model.Evento;
import com.leandro.webeventos.service.ClienteService;
import com.leandro.webeventos.service.CompraService;
import com.leandro.webeventos.service.EventoService;

@Controller
@RequestMapping("carrinho")
@SessionAttributes(names = {"carrinho"})
public class CarrinhoController {

	private EventoService service;
	private ClienteService clienteService;
	private CompraService compraService;
	private Carrinho carrinho;
	
	@Autowired
	public CarrinhoController(EventoService service, ClienteService clienteService,
			CompraService compraService, Carrinho carrinho) {
		this.service = service;
		this.clienteService = clienteService;
		this.compraService = compraService;
		this.carrinho = carrinho;
	}
	
	@GetMapping("")
	public String mostrarCarrinho(ModelMap model) {
		Set<CompraEvento> compraEventos = carrinho.getCompraEventos();
		model.addAttribute("total", carrinho.calcularTotal());
		model.addAttribute("compraEventos", compraEventos);
		return "carrinho/carrinho";
	}
	
	@GetMapping("adicionar/{id}")
	public String adicionar(@PathVariable("id") Long id, RedirectAttributes attr) {
		Cliente cliente = getCliente();
		Evento evento = service.buscarPorId(id);
		int ingressosParaComprar = compraService.podeComprar(cliente, evento);
		if(ingressosParaComprar > 0) {
			evento.transformaDados();
			CompraEvento compraEvento = new CompraEvento();
			compraEvento.setEvento(evento);
			compraEvento.setValorUnitario(evento.getPreco());
			compraEvento.setSubtotal(compraEvento.getValorUnitario() * compraEvento.getQtdIngresso());
			carrinho.addCompraEvento(compraEvento);
			attr.addFlashAttribute("alerta", "sucesso");
			attr.addFlashAttribute("texto", "Ingresso adicionado ao carrinho!");
		}else {
			attr.addFlashAttribute("alerta", "erro");
			attr.addFlashAttribute("texto", "Você já comprou o limite de  "+ evento.getLimiteCliente() + " ingressos por cliente!" );
		}
		
		return "redirect:/eventos/"+id;
	}
	
	@GetMapping("adicionarUm/{id}")
	public String adicionarUm(@PathVariable("id") Long id, RedirectAttributes attr) {
		for(CompraEvento ce : carrinho.getCompraEventos()) {
			if(ce.getEvento().getId() == id && compraService.podeComprar(getCliente(), ce.getEvento()) > ce.getQtdIngresso()) {
				ce.setQtdIngresso(ce.getQtdIngresso() + 1);
				ce.setSubtotal(ce.getValorUnitario() * ce.getQtdIngresso());
				return "redirect:/carrinho";
			}	
		}
		attr.addFlashAttribute("alerta", "erro");
		attr.addFlashAttribute("texto", "Você já comprou o limite de ingressos por cliente!" );
		return "redirect:/carrinho";
	}
	
	@GetMapping("/removerUm/{id}")
	public String removerUm(@PathVariable("id") Long id) {
		for(CompraEvento ce : carrinho.getCompraEventos()) {
			if((ce.getEvento().getId() == id) && (ce.getQtdIngresso() > 1)) {
				ce.setQtdIngresso(ce.getQtdIngresso() - 1);
				ce.setSubtotal(ce.getValorUnitario() * ce.getQtdIngresso());
			}
		}
		return "redirect:/carrinho";
	}
	
	@GetMapping("remover/{id}")
	public String remover(@PathVariable("id") Long id) {
		for(CompraEvento ce : carrinho.getCompraEventos()) {
			if(ce.getEvento().getId() == id) {
				carrinho.removeCompraEvento(ce);
				break;
			}
		}
		return "redirect:/carrinho";
	}
	
	@GetMapping("finalizar")
	public String finalizar() {
		Cliente cliente = getCliente();
		
		Compra compra = new Compra();
		compra.setCliente(cliente);
		compra.setCompraEventos(carrinho.getCompraEventos());
		compra.setHora(LocalDateTime.now());
		compra.setTotal(compra.calcularTotal());
		for(CompraEvento ce : compra.getCompraEventos()) {
			ce.setCompra(compra);
		}
		compraService.salvar(compra);
		
		return "carrinho/compra-finalizada";
	}

	private Cliente getCliente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<Cliente> cliente = clienteService.buscarPorUsuario(email);
		if(cliente.isPresent()) {
			return cliente.get();
		}else {
			return null;
		}
	}
}
