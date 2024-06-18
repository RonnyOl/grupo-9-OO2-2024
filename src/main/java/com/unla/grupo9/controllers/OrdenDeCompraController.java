package com.unla.grupo9.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo9.entities.OrdenDeCompra;
import com.unla.grupo9.entities.Proveedor;
import com.unla.grupo9.entities.Stock;
import com.unla.grupo9.entities.User;
import com.unla.grupo9.helpers.ViewRouteHelper;
import com.unla.grupo9.services.ILoteService;
import com.unla.grupo9.services.IOrdenDeCompraService;
import com.unla.grupo9.services.IProveedorService;
import com.unla.grupo9.services.IStockService;
import com.unla.grupo9.services.implementation.OrdenDeCompraService;
import com.unla.grupo9.services.implementation.UserService;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
@RequestMapping("/ordendecompra")
public class OrdenDeCompraController {

	// Vinculacion con el Service Principal
	private IOrdenDeCompraService ordenService;

	// Vinculacion con otros Service
	private IStockService stockService;
	private IProveedorService proveedorService;
	private ILoteService loteService;
	private UserService userService;

	// Constructor del Controlador
	public OrdenDeCompraController(IOrdenDeCompraService ordenCompraService, IStockService stockService,
			IProveedorService proveedorService, ILoteService loteService, UserService userService) {
		this.ordenService = ordenCompraService;
		this.stockService = stockService;
		this.proveedorService = proveedorService;
		this.loteService = loteService;
		this.userService = userService;
	}

	// Retorna la vista de una lista de todas las Ordenes de Compra
	@GetMapping("/lista")
	public ModelAndView ordenesDeCompra() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);

		List<OrdenDeCompra> lista = ordenService.traerOrdenDeCompra();
		List<Stock> lstStock = ordenService.findAllDistinctStock();
		
		modelAndView.addObject("lista", lista);
		modelAndView.addObject("listaStock", lstStock);

		return modelAndView;
	}

	// Retorna la vista individual de la orden de compra seleccionada por su id
	@GetMapping("/individual/{id}")
	public ModelAndView individualOrden(@PathVariable("id") int id) {
		ModelAndView modelAndView;
		Optional<OrdenDeCompra> objeto = ordenService.traerOrdenDeCompra(id);

		if (objeto.isPresent()) {
			modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
			modelAndView.addObject("ordenDeCompra", objeto.get());
		} else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);

		}
		return modelAndView;
	}

	/// Retorna la vista que muestra una lista de Ordenes de compra realizadas en la
	/// fecha seleccionada
	@GetMapping("/listafecha/{fecha}")
	public ModelAndView ordenesDeCompra(@PathVariable("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		List<OrdenDeCompra> lista = ordenService.traerOrdenDeCompra(fecha);

		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);
		modelAndView.addObject("lista", lista);

		return modelAndView;
	}

	/// Retorna la vista que muestra una lista de Ordenes de compra realizadas por
	/// el usuario logueado
	@GetMapping("/listausuario/")
	public ModelAndView ordenesDeCompra(@AuthenticationPrincipal UserDetails userDetails) {

		User user = userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		List<OrdenDeCompra> lista = ordenService.traerOrdenDeCompra(user);
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);
		modelAndView.addObject("lista", lista);

		return modelAndView;
	}

	/// Retorna la vista que muestra una lista de Ordenes de compra que tengan
	/// asignado el stock seleccionado
	@GetMapping("/listastock/{stock}")
	public ModelAndView ordenesDeCompra(@PathVariable("stock") int idStock) {

		ModelAndView modelAndView;
		Optional<Stock> stock = this.stockService.traerStock(idStock);

		if (stock.isPresent()) {

			modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);
			List<OrdenDeCompra> lista = this.ordenService.traerOrdenDeCompra(stock.get());
			List<Stock> lstStock = ordenService.findAllDistinctStock();

			modelAndView.addObject("listaStock", lstStock);
			modelAndView.addObject("lista", lista);
		} else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}

		return modelAndView;
	}

	/// Retorna la vista que muestra una lista de Ordenes de compra que tengan
	/// asignado el stock seleccionado y el usuario logueado
	@GetMapping("/listatienelote/{tienelote}")
	public ModelAndView ordenesDeCompra(@PathVariable("tienelote") boolean tieneLote) {

		ModelAndView modelAndView;
		List<OrdenDeCompra> lista = this.ordenService.traerOrdenDeCompra(tieneLote);

		
		if (lista != null) {
			modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);
			modelAndView.addObject("lista", lista);
			
		} else {
			modelAndView = new ModelAndView(ViewRouteHelper.ERROR_500);
		}

		return modelAndView;
	}

	// Retorna una vista para generar una Orden de compra que se vincula con el
	// Stock enviado por parametro
	@GetMapping("/nueva/{idStock}")
	public ModelAndView nuevaOrdenDeCompra(@PathVariable("idStock") int idStock) {

		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_ORDER);
		Optional<Stock> stockReal = stockService.traerStock(idStock);

		if (stockReal.isPresent()) {
			OrdenDeCompra orden = new OrdenDeCompra();
			orden.setStock(stockReal.get());
			List<Proveedor> lstProveedores = proveedorService.traerProveedores();

			modelAndView.addObject("orden", orden);
			modelAndView.addObject("proveedores", lstProveedores);
		}

		return modelAndView;
	}

	// Redirecciona a la vista individual de la orden de compra que se crea y se
	// guarda en la BD
	@PostMapping("/crear")
	public RedirectView agregarOrdenDeCompra(@ModelAttribute("orden") OrdenDeCompra ordenDeCompra,
			@AuthenticationPrincipal UserDetails userDetails) {

		Optional<Proveedor> nuevo = proveedorService.traerProveedor(ordenDeCompra.getProveedor().getIdProveedor());
		User user = userService.findByUsernameAndFetchUserRolesEagerly(userDetails.getUsername());
		if (nuevo.isPresent() && userDetails.isAccountNonExpired()) {
			ordenDeCompra.setProveedor(nuevo.get());
			ordenDeCompra.setUser(user);
			ordenDeCompra = ordenService.agregarOModificarOrdenDeCompra(ordenDeCompra);
			loteService.verificarYCrearLote();

		}

		return new RedirectView(ViewRouteHelper.ROUTE_INDI_ODC + "/individual/" + ordenDeCompra.getIdOrdenDeCompra());
	}

}