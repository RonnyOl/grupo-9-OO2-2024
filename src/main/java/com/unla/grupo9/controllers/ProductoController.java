package com.unla.grupo9.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.Stock;
import com.unla.grupo9.helpers.ViewRouteHelper;
import com.unla.grupo9.services.IProductoService;



//Controlador que gestiona a la entidad Producto
@Controller
@RequestMapping("/producto")
public class ProductoController {

	//Vinculacion de Servicio
	private IProductoService productService;
	
	//Constructor del controlador
	public ProductoController(IProductoService productService) {
		this.productService = productService;
	}

	//Retorna la vista de todos los Productos
	@GetMapping("/lista")
	public ModelAndView productoLista() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PRODUCTS);
		List<Producto> lista = productService.traerProductos();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	//Retorna la vista de un Producto individual accediendo por su id
	@GetMapping("/individual/{id}")	
	public ModelAndView productoIndividual(@PathVariable("id") int id) {
		
		ModelAndView modelAndView;
		Optional<Producto> objeto = productService.traerProducto(id);
		
		if(objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI);
			modelAndView.addObject("producto", objeto.get());
		}else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}
		
		return modelAndView;
	}
	
	//Retorna la vista para añadir un Producto
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/nuevo")
	public ModelAndView productoNuevo() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_PRODUCTO);
		modelAndView.addObject("nuevoProducto", new Producto());
		return modelAndView;
	}
	
	//Recibe el producto creado, lo carga a la BD y redirecciona a la lista de Producto
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crear")
	public RedirectView productoCrear(@ModelAttribute("nuevoProducto")Producto producto,@RequestParam("puntoMinimo") int puntoMinimo) {
		
		producto.setStock(new Stock(puntoMinimo,0,true,producto));
		producto = productService.agregarOModificarProducto(producto);
		
		return new RedirectView(ViewRouteHelper.RUTA_PRODUCTS);
	}
	
	//Retorna la vista para administrar un Producto	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/administrar/{id}")
	public ModelAndView productoAdministrar(@PathVariable("id") int id) {
		ModelAndView modelAndView;
		Optional<Producto> objeto = productService.traerProducto(id);
		if (objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_PRODUCTO);
			modelAndView.addObject("producto", objeto.get());
		}else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}
		return modelAndView;
	}
	
	//Recibe el id del Producto al que se le va a cambiar el estado y redirecciona a la vista individual de ese Producto
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/habilitacion/{id}")
	public RedirectView productoCambiarEstadoDeProducto(@PathVariable("id") int id) {
		RedirectView redirect;
		
		Optional<Producto> objeto = productService.traerProducto(id);
		if (objeto.isPresent()) {
		productService.cambiarEstadoDeProducto(objeto, !objeto.get().isHabilitado());
		 redirect = new RedirectView(ViewRouteHelper.ROUTE_INDI +"/individual" + "/"+ id);
		}else {
			redirect = new RedirectView(ViewRouteHelper.ERROR_500);
		}
		
		return redirect;
	}
	
	//Recibe el Producto modificado, lo actualiza y redirecciona a la vista individual de ese Producto
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/modificar")
	public RedirectView productoModificar(@ModelAttribute("producto") Producto producto) {
		RedirectView redirect;
		
		Optional<Producto> p = productService.traerProducto(producto.getIdProducto());
		if (p.isPresent()) {
			productService.agregarOModificarProducto(producto);
			redirect = new RedirectView(ViewRouteHelper.ROUTE_INDI +"/individual" + "/"+ p.get().getIdProducto());
		}else {
			redirect = new RedirectView(ViewRouteHelper.ERROR_500);
		}
		return redirect;
	}
	
}
