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

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IProductoService;




@Controller
@RequestMapping("/producto")

public class ProductoController {

	private IProductoService productService;
	
	public ProductoController(IProductoService productService) {
		this.productService = productService;
	}

	//Retorna la vista de todos los productos
	
	@GetMapping("/lista")
	public ModelAndView productos() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PRODUCTS);

		
		List<Producto> lista = productService.traerProductos();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")	
	public ModelAndView individual(@PathVariable("id") int id) {
		
		ModelAndView modelAndView;
		Optional<Producto> objeto = productService.traerProducto(id);
		
		if(objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI);
			modelAndView.addObject("producto", objeto.get());
		}else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_403);
		}
		
		return modelAndView;
	}
	
	
	@GetMapping("/nuevo")
	public ModelAndView nuevoProducto() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_PRODUCTO);
		modelAndView.addObject("nuevoProducto", new Producto());
		return modelAndView;
	}
	
	
	@PostMapping("/crear")
	public RedirectView create(@ModelAttribute("nuevoProducto")Producto producto,@RequestParam("puntoMinimo") int puntoMinimo) {


		
		producto.setStock(new Stock(puntoMinimo,0,true,producto));
		producto = productService.agregarOModificarProducto(producto);

		return new RedirectView(ViewRouteHelper.RUTA_PRODUCTS);
	}
	

	@GetMapping("/administrar/{id}")
	public ModelAndView administrarProducto(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_PRODUCTO);
		Optional<Producto> objeto = productService.traerProducto(id);
		modelAndView.addObject("producto", objeto.get());
		return modelAndView;
	}
	
	@PostMapping("/habilitacion/{id}")
	public RedirectView cambiarEstadoDeProducto(@PathVariable("id") int id) {
		
		Optional<Producto> objeto = productService.traerProducto(id);
		productService.cambiarEstadoDeProducto(objeto, !objeto.get().isHabilitado());
		return new RedirectView(ViewRouteHelper.ROUTE_INDI +"/individual" + "/"+ id);
	}
	
	@PostMapping("/modificar")
	public RedirectView cambiarEstadoDeProducto(@ModelAttribute("producto") Producto producto) {
		
		Optional<Producto> p = productService.traerProducto(producto.getIdProducto());
		if (p.isPresent()) {
			productService.agregarOModificarProducto(producto);
		}
		
		
		return new RedirectView(ViewRouteHelper.ROUTE_INDI +"/individual" + "/"+ p.get().getIdProducto());
	}
	
}
