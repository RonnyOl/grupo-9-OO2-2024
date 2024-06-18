package com.unla.grupo3.controllers;

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

import com.unla.grupo3.entities.Lote;

import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.ILoteService;
import com.unla.grupo3.services.IStockService;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
@RequestMapping("/lote")

public class LoteController {

	// Vinculacion con el Service Principal
	private ILoteService loteService;

	// Vinculacion con otros services
	private IStockService stockService;

	// Constructor de controlador
	public LoteController(ILoteService loteService, IStockService stockService) {
		this.loteService = loteService;
		this.stockService = stockService;

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
	@GetMapping("/Lista")
	public ModelAndView traerLote(@PathVariable("fecha") LocalDate fecha) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_LOTE);

		List<Lote> lista = loteService.traerLote(fecha);
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}

	// Cambia el estado del lote a aceptado
	@PostMapping("/aceptacion/{id}")
	public RedirectView cambiarEstadoDeLote(@PathVariable("id") int id) {

		Optional<Lote> lote = loteService.traerLote(id);

		RedirectView redirect = new RedirectView(ViewRouteHelper.ERROR_500);

		if (lote.isPresent()) {

			loteService.cambiarEstadoDeLote(lote, true);
			stockService.sumarStock(lote.get().getOrdenDeCompra().getStock(),
					lote.get().getOrdenDeCompra().getCantidadAComprar());
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
