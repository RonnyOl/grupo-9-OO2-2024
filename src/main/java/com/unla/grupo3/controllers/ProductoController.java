package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.implementation.ProductoService;



@Controller
@RequestMapping("/producto")
public class ProductoController {

	private ProductoService productService;
	
	public ProductoController(ProductoService productService) {
		this.productService = productService;
	}

	@GetMapping("/lista")
	public ModelAndView productos() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PRODUCTS);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Producto> lista = productService.traerProductos();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")
	
	public ModelAndView individual(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<Producto> objeto = productService.traerProducto(id);
		modelAndView.addObject("producto", objeto.get());
		return modelAndView;
	}

	
}
