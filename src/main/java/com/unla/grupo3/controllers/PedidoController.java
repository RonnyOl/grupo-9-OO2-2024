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
import com.unla.grupo3.services.IProductoService;
import com.unla.grupo3.services.IStockService;
import com.unla.grupo3.services.implementation.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;
	private IProductoService productoService;
	private IStockService stockService;
	private UserService userService;
	
	public PedidoController(IPedidoService pedidoService, IProductoService productoService, UserService userService, IStockService stockService) {
		this.pedidoService = pedidoService;
		this.productoService=productoService;
		this.stockService=stockService;
		this.userService=userService;
	}
	
// 	PediosListaAdmin mostrará todos los pedidos
//	realizados por los distintos usuarios inclusive del mismo admin
	@GetMapping("/pedidosrealizados")
	public ModelAndView pedidosListaAdmin(@AuthenticationPrincipal UserDetails userDetails) {	
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		User user= userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		List<Pedido> lista = pedidoService.traerListaPedidoPorUsuario(user);
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", true);
		return modelAndView;
	}
	// pedidosLista mostrará todos los pedidos realizados por el usuario logeado.
	// Al reutilizar la misma vista que pedidosListaAdmin, el btnVer lo que hace es que
	// el botón que solo el admin pueda ver en pedidosListaAdmin no esté en este metodo.
	@GetMapping("/pedidos")
	public ModelAndView pedidosLista() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		List<Pedido> lista = pedidoService.traerPedido();
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", false);
		return modelAndView;
	}
	
	// pedidosIndividual mostrará el pedido individual mediante la id
	@GetMapping("/individual/{id}")													
	public ModelAndView pedidosIndividual(@PathVariable("id") int id) {
		ModelAndView modelAndView;
		Optional<Pedido> objeto = pedidoService.traerPedido(id);
		
		if(objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
			modelAndView.addObject("pedido", objeto.get());
		}else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}
		
		return modelAndView;
	}
	// Trae una lista segun el producto seleccionado
	@GetMapping("/pedidosrealizados/{idProducto}")
	public ModelAndView listaPorProducto(@PathVariable("idProducto")int idProducto){
		ModelAndView modelAndView;
		System.out.println(idProducto);
		Optional<Producto> producto= productoService.traerProducto(idProducto);
		System.out.println(producto.get().getDescripcion());
		if (producto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
			List<Pedido> lista = pedidoService.traerListaPedidoPorProducto(producto.get());
			modelAndView.addObject("lista ",lista);
			modelAndView.addObject("btnVer", true);
		}else {
			System.out.println("holaaa");
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}
		
		return modelAndView;
	}
	// PedidoNuevo es el post para que cuando se realice una compra, se realice el pedido
	@PostMapping("/venta/{idProducto}")
 	public RedirectView pedidoNuevo(@PathVariable("idProducto") int idProducto, @RequestParam int cantidadAComprar, @AuthenticationPrincipal UserDetails userDetails) {
		
		Optional<Producto> producto =productoService.traerProducto(idProducto);

		RedirectView redirect;
		User user= userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername()); // Se obtiene el Usuario para posteriormente setearlo al pedido
		
		if (producto.isPresent() && userDetails.isAccountNonExpired()) {
			pedidoService.agregarOModificarPedido(new Pedido(user,cantidadAComprar,producto.get())); //Se guarda el pedido en la bd
			// Al comprar, se debe realizar un baja en el stock, por lo cuál se resta segun la cantidad que se haya comprado
			stockService.restarStock(producto.get().getStock(), cantidadAComprar); 
			//Al restar el stock, también se debe corroborar si ha alcanzado al punto minimo, por lo que se ejecuta
			//reabastecer
			stockService.validarRabastecer(producto.get().getStock().getIdStock()); 
			redirect = new RedirectView(ViewRouteHelper.ROUTE_ORDERS);
		}else {
			redirect = new RedirectView(ViewRouteHelper.ERROR_500);
		}

		return redirect; 
	}

	
}