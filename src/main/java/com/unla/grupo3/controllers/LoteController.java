package com.unla.grupo3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.Lote;
import com.unla.grupo3.events.LoteCreadoEvent;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.ILoteService;


@Controller
@RequestMapping("/lote")

public class LoteController {
	

	private ILoteService loteService;
	

	public LoteController(ILoteService loteService) {
		this.loteService = loteService;
	}

	@GetMapping("/lista")
	public ModelAndView Lotes() {								
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.LOTES);
		List<Lote> lista = loteService.traerLote();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")	
	public ModelAndView individual(@PathVariable("id") int id) {	
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_LOTE);

		System.out.println(id);
		Optional<Lote> objeto = loteService.traerLote(id);
		modelAndView.addObject("lote", objeto.get());
		return modelAndView;
	}
	
	@PostMapping("/aceptacion/{id}")		
	public RedirectView cambiarEstadoDeLote(@PathVariable("id") int id) {
		
		Optional<Lote> objeto = loteService.traerLote(id);
		loteService.cambiarEstadoDeLote(objeto, !objeto.get().isAceptado());
		return new RedirectView(ViewRouteHelper.ROUTE_LOTE +"/individual" + "/"+ id);
	}
	
	
	@PostMapping("/modificar")					///agregar HELPER
	public RedirectView cambiarEstadoDeLote(@ModelAttribute("lote") Lote lote) {
		
		Optional<Lote> l = loteService.traerLote(lote.getIdLote());
		if (l.isPresent()) {
			loteService.agregarOModificarLote(lote);
		}
		return new RedirectView(ViewRouteHelper.ROUTE_LOTE +"/individual" + "/"+ l.get().getIdLote());
	}
	
	//Este metodo no tiene una ruta directa de acceso, sino que se va a ejecutar cuando se cree el evento que dispara la generacion de un nuevo lote
	//Y que provoca que se envie  a una nueva vista para ver el nuevo lote creado y aceptar o no el nuevo lote que se creó
	@EventListener(LoteCreadoEvent.class) 
	public ModelAndView mostrarNuevoLote(LoteCreadoEvent event) {
		
		Lote loteRecibido = event.getLoteCreado();
		List<Lote> lotesSinAceptar = loteService.findAllByAceptadoFalse();
		return this.individual(lotesSinAceptar.size());
	}

}
