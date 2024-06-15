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

@Controller
@RequestMapping("/stock")
public class StockController {
	
	private IStockService stockService;
	private IProductoService productoService;
	
	
	public StockController(IStockService stockService, IProductoService productoService) {
		super();
		this.stockService = stockService;
		this.productoService = productoService;
	}


	@GetMapping("/listaStock")
	public ModelAndView stocks() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.STOCKS);
		
		List<Stock> lista = stockService.traerStock();
		modelAndView.addObject("lista", lista);
		return modelAndView;
		
	}
	
	@GetMapping("/stockIndividual/{id}")
	public ModelAndView stockIndividual(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_STOCK);
		
		Optional<Stock> objeto = stockService.traerStock(id);
		modelAndView.addObject("stock", objeto.get());
		return modelAndView;
		
		
	}
	
	@GetMapping("/stockIndividualProducto/{idProducto}")
	public ModelAndView stockPorProducto(@PathVariable("idProducto")int idProducto) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_STOCK);
		
		Optional<Producto> producto = productoService.traerProducto(idProducto);
		Optional<Stock> stock = stockService.traerStock(producto.get()); 
		
		modelAndView.addObject("stock", stock.get());
		return modelAndView;
	}
	
}
	

