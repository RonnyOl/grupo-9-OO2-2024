package com.unla.grupo9.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
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

import com.unla.grupo9.entities.Pedido;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.User;
import com.unla.grupo9.entities.UserRole;
import com.unla.grupo9.helpers.ViewRouteHelper;
import com.unla.grupo9.services.IPedidoService;
import com.unla.grupo9.services.IProductoService;
import com.unla.grupo9.services.IStockService;
import com.unla.grupo9.services.implementation.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private IPedidoService pedidoService;
	private IProductoService productoService;
	private IStockService stockService;
	private UserService userService;

	public PedidoController(IPedidoService pedidoService, IProductoService productoService, UserService userService,
			IStockService stockService) {
		this.pedidoService = pedidoService;
		this.productoService = productoService;
		this.stockService = stockService;
		this.userService = userService;
	}

	// pedidosLista mostrará todos los pedidos realizados por el usuario logeado.
	// Al reutilizar la misma vista que pedidosListaAdmin, el btnVer lo que hace es
	// que
	// el botón que solo el admin pueda ver en pedidosListaAdmin no esté en este
	// metodo.
	@GetMapping("/pedidosrealizados")
	public ModelAndView pedidosLista(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		User user = userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());

		List<Pedido> lista = pedidoService.traerListaPedidoPorUsuario(user);
		List<Producto> listaProductos = pedidoService.findAllDistinctPedidoByUser(user);

		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", true);
		modelAndView.addObject("listaP", listaProductos);
		return modelAndView;
	}

// 	PediosListaAdmin mostrará todos los pedidos
//	realizados por los distintos usuarios inclusive del mismo admin
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/pedidos")
	public ModelAndView pedidosListaAdmin() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
		List<Pedido> lista = pedidoService.traerPedido();
		List<Producto> listaProductos = pedidoService.findAllDistinctPedido();
		modelAndView.addObject("listaP", listaProductos);
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("btnVer", false);
		return modelAndView;
	}

	// pedidosIndividual mostrará el pedido individual mediante la id
	@GetMapping("/individual/{id}")
	public ModelAndView pedidosIndividual(@PathVariable("id") int id) {
		ModelAndView modelAndView;
		Optional<Pedido> objeto = pedidoService.traerPedido(id);

		if (objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI_PEDIDO);
			modelAndView.addObject("pedido", objeto.get());
		} else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}

		return modelAndView;
	}

	// Trae una lista segun el producto seleccionado
	@GetMapping("/pedidosrealizados/{idProducto}")
	public ModelAndView listaPorProducto(@PathVariable("idProducto") int idProducto,
			@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView modelAndView;
		Optional<Producto> producto = productoService.traerProducto(idProducto);
		
		//se realiza una validacion para saber si se encuentra en la sesion admin o cliente
		User user = userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());


		if (producto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.PEDIDOS);
			List<Pedido> lista;
			List<Producto> listaProductos;

	        // Obtener los roles del usuario
	        boolean isAdmin = userDetails.getAuthorities().stream()
	            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
			
	        
	        //verifica si es admin
			if (isAdmin==true) {
				lista=pedidoService.traerListaPedidoPorProducto(producto.get());
				listaProductos = pedidoService.findAllDistinctPedido();
			} else {
				lista=pedidoService.traerListaPedidoPorProducto(producto.get());
				listaProductos = pedidoService.findAllDistinctPedidoByUser(user);
			}

			modelAndView.addObject("lista", lista);
			modelAndView.addObject("listaP", listaProductos);
			modelAndView.addObject("btnVer", true);
		} else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}

		return modelAndView;
	}

	// PedidoNuevo es el post para que cuando se realice una compra, se realice el
	// pedido
	@PostMapping("/venta/{idProducto}")
	public RedirectView pedidoNuevo(@PathVariable("idProducto") int idProducto, @RequestParam int cantidadAComprar,
			@AuthenticationPrincipal UserDetails userDetails) {

		Optional<Producto> producto = productoService.traerProducto(idProducto);

		RedirectView redirect;
		User user = userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername()); // Se obtiene el
																									// Usuario para
																									// posteriormente
																									// setearlo al
																									// pedido

		if (producto.isPresent() && userDetails.isAccountNonExpired()) {
			pedidoService.agregarOModificarPedido(new Pedido(user, cantidadAComprar, producto.get())); // Se guarda el
																										// pedido en la
																										// bd
			// Al comprar, se debe realizar un baja en el stock, por lo cuál se resta segun
			// la cantidad que se haya comprado
			stockService.restarStock(producto.get().getStock(), cantidadAComprar);
			productoService.validarCantidad(producto);
			// Al restar el stock, también se debe corroborar si ha alcanzado al punto
			// minimo, por lo que se ejecuta
			// reabastecer
			stockService.validarRabastecer(producto.get().getStock().getIdStock());
			redirect = new RedirectView(ViewRouteHelper.ROUTE_ORDERS);
		} else {
			redirect = new RedirectView(ViewRouteHelper.ERROR_500);
		}

		return redirect;
	}

}