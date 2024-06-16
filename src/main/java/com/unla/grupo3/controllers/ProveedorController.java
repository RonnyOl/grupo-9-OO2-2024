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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.Proveedor;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IProveedorService;


@Controller
@RequestMapping("/proveedor")

public class ProveedorController {
	

	private IProveedorService proveedorService;
	

	public ProveedorController(IProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}

	@GetMapping("/lista")
	public ModelAndView Proveedores() {								
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PROVEEDORES);
		List<Proveedor> lista = proveedorService.traerProveedores();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	@GetMapping("/individual/{id}")	
	public ModelAndView individual(@PathVariable("id") int id) {	
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_PROVEEDOR);

		System.out.println(id);
		Optional<Proveedor> objeto = proveedorService.traerProveedor(id);
		modelAndView.addObject("proveedor", objeto.get());
		return modelAndView;
	}
	
	@GetMapping("/nuevo")
	public ModelAndView nuevoProveedor() {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_PROVEEDOR);
		modelAndView.addObject("nuevoProveedor", new Proveedor());
		
		return modelAndView;
	}
	
	@PostMapping("/crear")
	public RedirectView create(@ModelAttribute("nuevoProveedor")Proveedor proveedor) {
		System.out.println(proveedor.getNombreEmpresa());
		
		proveedor = proveedorService.agregarModificarProveedor(proveedor);
		System.out.println("---............>"+proveedor.getIdProveedor());
		return new RedirectView(ViewRouteHelper.ROUTE_PROVEEDOR);
	}

	
	@PostMapping("/modificar")					
	public RedirectView cambiarEstadoDeProveedor(@ModelAttribute("p") Proveedor proveedor) {
		
		Optional<Proveedor> p = proveedorService.traerProveedor(proveedor.getIdProveedor());
		if (p.isPresent()) {
			proveedorService.agregarModificarProveedor(proveedor);
		}
		return new RedirectView(ViewRouteHelper.ROUTE_PROVEEDOR +"/individual" + "/"+ p.get().getIdProveedor());
	}
	
	@GetMapping("/administrar/{id}")
	public ModelAndView administrarProveedor(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_PROVEEDOR);
		Optional<Proveedor> objeto = proveedorService.traerProveedor(id);
		modelAndView.addObject("proveedor", objeto.get());
		return modelAndView;
	}
}