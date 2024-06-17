package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IPedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;

	
	public PedidoController(IPedidoService pedidoService) {
		this.pedidoService = pedidoService;

	}

	@GetMapping("/pedidosrealizados")
	public ModelAndView PedidosRealizados(@AuthenticationPrincipal UserDetails userDetails) {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		User user= pedidoService.getUserService().findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		List<Pedido> lista = pedidoService.traerListaPedidoPorUsuario(user);
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", true);
		return modelAndView;
	}
	
	@GetMapping("/pedidos")
	public ModelAndView Pedidos() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		List<Pedido> lista = pedidoService.traerPedido();
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", false);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")													
	public ModelAndView individualPedidos(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
		
		
		Optional<Pedido> objeto = pedidoService.traerPedido(id);
		
		if(objeto.isPresent()) modelAndView.addObject("pedido", objeto.get());
		
		return modelAndView;
	}
	
	@GetMapping("/{idProducto}")
	public ModelAndView individualPorProducto(@PathVariable("idProducto")int idProducto){
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
		
		Optional<Producto> producto= pedidoService.getProductoService().traerProducto(idProducto);
		
		if (producto.isPresent()) {
			Optional<Pedido> pedido = pedidoService.traerPedido(producto.get());
			
			if (pedido.isPresent()) {
				modelAndView.addObject("pedido", pedido.get());
			}
		}
		
		return modelAndView;
	}
	
	@PostMapping("/venta/{idProducto}")
 	public RedirectView nuevoPedido(@PathVariable("idProducto") int idProducto, @RequestParam int cantidadAComprar, @AuthenticationPrincipal UserDetails userDetails) {
		
		Optional<Producto> producto =pedidoService.getProductoService().traerProducto(idProducto);

		
		User user= pedidoService.getUserService().findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		
		if (producto.isPresent() && userDetails.isAccountNonExpired()) {
			pedidoService.agregarOModificarPedido(new Pedido(user,cantidadAComprar,producto.get()));
			
			pedidoService.getProductoService().getStockService().restarStock(producto.get().getStock(), cantidadAComprar);
			pedidoService.getProductoService().getStockService().validarRabastecer(producto.get().getStock().getIdStock()); 
			
		}
		
		return new RedirectView (ViewRouteHelper.ROUTE_ORDERS);//DEBE REDIRECCIONAR A UNA LISTA QUE MUESTRE LOS PEDIDOS HECHOS POR ESTE USUARIO
	}

	
}