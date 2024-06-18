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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo9.entities.Proveedor;
import com.unla.grupo9.helpers.ViewRouteHelper;
import com.unla.grupo9.services.IProveedorService;
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/proveedor")
public class ProveedorController {

	// Vinculacion con el service principal
	private IProveedorService proveedorService;

	// Constructor del controlador
	public ProveedorController(IProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}

	// Retorna una vista de la lista de todos los proveedores
	@GetMapping("/lista")
	public ModelAndView proveedorLista() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.PROVEEDORES);

		List<Proveedor> lista = proveedorService.traerProveedores();
		modelAndView.addObject("lista", lista);
		
		return modelAndView;
	}

	// Retorna una vista de un proveedor en particular
	@GetMapping("/individual/{id}")
	public ModelAndView traerProveedor(@PathVariable("id") int id) {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		Optional<Proveedor> objeto = proveedorService.traerProveedor(id);

		if (objeto.isPresent()) {

			modelAndView = new ModelAndView(ViewRouteHelper.INDI_PROVEEDOR);
			modelAndView.addObject("proveedor", objeto.get());
		}

		return modelAndView;
	}

	// Retorna una vista para crear un proveedor
	@GetMapping("/nuevo")
	public ModelAndView nuevoProveedor() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_PROVEEDOR);

		modelAndView.addObject("nuevoProveedor", new Proveedor());
		return modelAndView;
	}

	// Crea un proveedor
	@PostMapping("/crear")
	public RedirectView crearProveedor(@ModelAttribute("nuevoProveedor") Proveedor proveedor) {

		proveedor = proveedorService.agregarModificarProveedor(proveedor);
		return new RedirectView(ViewRouteHelper.ROUTE_PROVEEDOR);
	}

	// Retorna una vista para administrar un proveedor
	@GetMapping("/administrar/{id}")
	public ModelAndView administrarProveedor(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);

		Optional<Proveedor> objeto = proveedorService.traerProveedor(id);

		if (objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.ADMIN_PROVEEDOR);
			modelAndView.addObject("proveedor", objeto.get());
		}

		return modelAndView;
	}

	// Modificar un proveedor y guarda los cambios
	@PostMapping("/modificar")
	public RedirectView modificarProveedor(@ModelAttribute("p") Proveedor proveedor) {
		RedirectView rediect = new RedirectView(ViewRouteHelper.ERROR_500);

		Optional<Proveedor> p = proveedorService.traerProveedor(proveedor.getIdProveedor());
		if (p.isPresent()) {
			proveedorService.agregarModificarProveedor(proveedor);
			rediect = new RedirectView(
					ViewRouteHelper.ROUTE_PROVEEDOR + "/individual" + "/" + p.get().getIdProveedor());

		}
		return rediect;
	}
}
