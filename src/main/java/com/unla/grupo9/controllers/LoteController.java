package com.unla.grupo9.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo9.entities.Lote;
import com.unla.grupo9.helpers.ViewRouteHelper;
import com.unla.grupo9.services.ILoteService;
import com.unla.grupo9.services.IProductoService;
import com.unla.grupo9.services.IStockService;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
@RequestMapping("/lote")

public class LoteController {

	// Vinculacion con el Service Principal
	private ILoteService loteService;

	// Vinculacion con otros services
	private IStockService stockService;
	private IProductoService productoService;

	// Constructor de controlador
	public LoteController(ILoteService loteService, IStockService stockService,IProductoService productoService) {
		this.loteService = loteService;
		this.stockService = stockService;
		this.productoService=productoService;

	}

	// Retorna la vista de una lista de todos los lotes que existen
	@GetMapping("/lista")
	public ModelAndView traerLote() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTES);
		List<Lote> lista = loteService.traerLote();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}

	// Retorna la vista de un lote de forma individual por su id
	@GetMapping("/individual/{id}")
	public ModelAndView traerLote(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);

		Optional<Lote> objeto = loteService.traerLote(id);
		if (objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI_LOTE);
			modelAndView.addObject("lote", objeto.get());
		}

		return modelAndView;
	}

	// Retorna una vista de una lista de lotes filtrado por una fecha especifica
	@GetMapping("/lista/filtro/{fecha}")
	public ModelAndView traerLote(@PathVariable("fecha") LocalDate fecha) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTES);
		List<Lote> lista = loteService.findAllByfechaRecepcion(fecha);	
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	// Retorna una vista de una lista de lotes que no fueron aceptados
	@GetMapping("/lista/aceptado/{estado}")
	public ModelAndView traerLote(@PathVariable("estado") boolean estado) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTES);
		
		List<Lote> lista = loteService.traerLote(estado);
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	// Cambia el estado del lote a aceptado
	
	
	@PostMapping("/aceptacion/{id}")
	public RedirectView cambiarEstadoDeLote(@PathVariable("id") int id) {

		Optional<Lote> lote = loteService.traerLote(id);

		RedirectView redirect = new RedirectView(ViewRouteHelper.ERROR_500);

		if (lote.isPresent()) {
			//Se cambia el estado de lote a aceptado
			loteService.cambiarEstadoDeLote(lote, true);
			
			//Se suma al stock asignado a la orden de compra del lote la cantidad que ingreso
			stockService.sumarStock(lote.get().getOrdenDeCompra().getStock(),
					lote.get().getOrdenDeCompra().getCantidadAComprar());
			
			//Se verifica que el producto pueda dejar de estar deshabilitado si es que lo estaba antes de recibir la mercaderia
			productoService.validarCantidad( Optional.of(lote.get().getOrdenDeCompra().getStock().getProducto()) );
			
			//Se valida si se necesita reabastecer nuevamente el stock
			stockService.validarRabastecer(lote.get().getOrdenDeCompra().getStock().getIdStock());
			
			

			redirect = new RedirectView(ViewRouteHelper.ROUTE_LOTE + "/individual" + "/" + id);

		}

		return redirect;
	}

	/*
	 * FALTA IMPLEMENTAR // Modifica un lote
	 * 
	 * @PostMapping("/modificar") public RedirectView
	 * modificarLote(@ModelAttribute("lote") Lote lote) {
	 * 
	 * Optional<Lote> l = loteService.traerLote(lote.getIdLote()); if
	 * (l.isPresent()) { loteService.agregarOModificarLote(lote); } return new
	 * RedirectView(ViewRouteHelper.ROUTE_LOTE + "/individual" + "/" +
	 * l.get().getIdLote()); }
	 */

}
