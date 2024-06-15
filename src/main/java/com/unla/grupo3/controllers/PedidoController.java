package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IPedidoService;
import com.unla.grupo3.services.IProductoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;
	private IProductoService productoService;
	
	public PedidoController(IPedidoService pedidoService, IProductoService productoService) {
		this.pedidoService = pedidoService;
		this.productoService=productoService;
	}

	@GetMapping("/pedidosRealizados")
	public ModelAndView Pedidos() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		
		List<Pedido> lista = pedidoService.traerPedido();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/{id}")													
	public ModelAndView individualPedidos(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
		
		
		Optional<Pedido> objeto = pedidoService.traerPedido(id);
		
		if(objeto.isPresent()) modelAndView.addObject("Pedido", objeto.get());
		
		return modelAndView;
	}
	
	@GetMapping("/{idProducto}")
	public ModelAndView individualPorProducto(@PathVariable("idProducto")int idProducto){
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
		
		Optional<Producto> producto= productoService.traerProducto(idProducto);
		
		if (producto.isPresent()) {
			Optional<Pedido> pedido = pedidoService.traerPedido(producto.get());
			
			if (pedido.isPresent()) {
				modelAndView.addObject("pedido", pedido.get());
			}
		}
		
		return modelAndView;
	}
	
	@PostMapping("/venta")
 	public RedirectView nuevoPedido(@ModelAttribute("pedido") Pedido pedido) {
		
		
		
		return new RedirectView ();//DEBE REDIRECCIONAR A UNA LISTA QUE MUESTRE LOS PEDIDOS HECHOS POR ESTE USUARIO
	}

	
}