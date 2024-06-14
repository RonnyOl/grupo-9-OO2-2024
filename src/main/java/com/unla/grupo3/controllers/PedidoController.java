package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IPedidoService;





@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;
	
	public PedidoController(IPedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/lista")
	public ModelAndView Pedidos() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Pedido> lista = pedidoService.traerPedido();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")
														
	public ModelAndView individualPedidos(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<Pedido> objeto = pedidoService.traerPedido(id);
		modelAndView.addObject("Pedido", objeto.get());
		return modelAndView;
	}

	
}