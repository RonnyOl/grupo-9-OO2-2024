package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.unla.grupo3.entities.User;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IPedidoService;
import com.unla.grupo3.services.IProductoService;
import com.unla.grupo3.services.implementation.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;
	private IProductoService productoService;
	private UserService userService;
	
	public PedidoController(IPedidoService pedidoService, IProductoService productoService, UserService userService) {
		this.pedidoService = pedidoService;
		this.productoService=productoService;
		this.userService=userService;
	}

	@GetMapping("/pedidosrealizados")
	public ModelAndView Pedidos(@AuthenticationPrincipal UserDetails userDetails) {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		User user= userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		List<Pedido> lista = pedidoService.traerListaPedidoPorUsuario(user);
		modelAndView.addObject("lista", lista);
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
		
		Optional<Producto> producto= productoService.traerProducto(idProducto);
		
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
		
		Optional<Producto> producto =productoService.traerProducto(idProducto);
		System.out.println(producto.get().getNombre());
		System.out.println(cantidadAComprar);
		User user= userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		
		if (producto.isPresent() && userDetails.isAccountNonExpired()) {
			pedidoService.agregarOModificarPedido(new Pedido(user,cantidadAComprar,producto.get()));
			productoService.modificarStockProducto(producto.get(), cantidadAComprar);
		}
		
		return new RedirectView (ViewRouteHelper.ROUTE_ORDERS);//DEBE REDIRECCIONAR A UNA LISTA QUE MUESTRE LOS PEDIDOS HECHOS POR ESTE USUARIO
	}

	
}