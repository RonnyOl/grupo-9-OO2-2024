package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IProductoService;
import com.unla.grupo3.services.IStockService;


 ///CONFIGURAR LOS HELPERS
/// areglar tema listas


@Controller
@RequestMapping("/stock")
public class StockController {

	private IStockService stockService;
	
	private IProductoService productoService;
	
	
	public StockController(IStockService stockService, IProductoService productoService) {
		this.stockService = stockService;
		this.productoService = productoService;
		
	}

	@GetMapping("/lista")
	public ModelAndView Stocks() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Stock> lista = stockService.traerStock();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	/// TRAER STOCK FILTRADO POR SU PRODUCTO
	
	@GetMapping("/particular/{id}")				
	public ModelAndView individualStock(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		
		Optional<Producto> p = productoService.traerProducto(id);
		List<Stock> lista = stockService.traerStock(p.get());
		modelAndView.addObject("stock", lista);
		return modelAndView;
	}
	
	
	
	///POR ID
	@GetMapping("/particular/{id}")				
	public ModelAndView individualStockID(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<Stock> objeto = stockService.traerStock(id);
		modelAndView.addObject("stock", objeto.get());
		return modelAndView;
	}

	
}