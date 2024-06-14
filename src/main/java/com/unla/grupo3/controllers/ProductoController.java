package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IProductoService;



@Controller
@RequestMapping("/producto")
public class ProductoController {

	private IProductoService productService;
	
	public ProductoController(IProductoService productService) {
		this.productService = productService;
	}

	@GetMapping("/lista")
	public ModelAndView productos() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PRODUCTS);

		
		List<Producto> lista = productService.traerProductos();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")	
	public ModelAndView individual(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI);

		
		Optional<Producto> objeto = productService.traerProducto(id);
		modelAndView.addObject("producto", objeto.get());
		return modelAndView;
	}
	
	
	@GetMapping("/nuevo")
	public ModelAndView nuevoProducto() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_PRODUCTO);
		
		return modelAndView;
	}

	@GetMapping("/administrar")
	public ModelAndView administrarProducto() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_PRODUCTO);
		return modelAndView;
	}
	
}
