package com.unla.grupo3.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Proveedor;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.helpers.ViewRouteHelper;
import com.unla.grupo3.services.IOrdenDeCompraService;
import com.unla.grupo3.services.IProveedorService;
import com.unla.grupo3.services.IStockService;


@Controller
@RequestMapping("/ordendecompra")
public class OrdenDeCompraController {

	private IOrdenDeCompraService ordenService;
	private IStockService stockService;
	private IProveedorService proveedorService;
	
	public OrdenDeCompraController(IOrdenDeCompraService ordenCompraService,IStockService stockService,IProveedorService proveedorService) {
		this.ordenService = ordenCompraService;
		this.stockService = stockService;
		this.proveedorService= proveedorService;
		
	}
	
	// TODOS

	@GetMapping("/lista")
	public ModelAndView ordenesDeCompra() {							 
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.ORDERS);
		
		List<OrdenDeCompra> lista = ordenService.traerOrdenDeCompra();
		modelAndView.addObject("lista", lista);
		return modelAndView;
	}
	
	//POR ID	
	@GetMapping("/individual/{id}")														
	public ModelAndView individualOrden(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<OrdenDeCompra> objeto = ordenService.traerOrdenDeCompra(id);
		modelAndView.addObject("ordenDeCompra", objeto.get());
		return modelAndView;
	}

	
	/// POR FECHA
	@GetMapping("/individualPorFecha/{id}")	
	public ModelAndView individualOrden(@PathVariable("f") LocalDate f) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<OrdenDeCompra> objeto = ordenService.traerOrdenDeCompra(f);
		modelAndView.addObject("ordenDeCompra", objeto.get());
		return modelAndView;
	}
	/// POR USER
	/// la estructura esta armada, hay que cambiar varias cosas
	
	 /*
	 @GetMapping("/particular/{id}")				
	 	public ModelAndView individualStock(@PathVariable("producto") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		
		Optional<Stock> objeto = stockService.traerStock(p.get());
		modelAndView.addObject("stock", objeto.get());
		return modelAndView;
	}
	*/
	
	/// POR STOCK
	
	@GetMapping("/individual/stock/{id}")				
	public ModelAndView individualOrdenCompra(@PathVariable("stock") int id) {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDI_ORDER);
		
		Optional<Stock> p = this.stockService.traerStock(id);
		List<OrdenDeCompra> lista = this.ordenService.traerOrdenDeCompra(p.get());	
		modelAndView.addObject("ordenCompra", lista);
		return modelAndView;
	}
	
	@GetMapping("/nueva/{idStock}")
	public ModelAndView nuevaOrdenDeCompra(@PathVariable("idStock") int idStock ) {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.NEW_ORDER);
		Optional<Stock> stockReal = stockService.traerStock(idStock);


		if (stockReal.isPresent()) {
			OrdenDeCompra orden = new OrdenDeCompra();
			orden.setStock(stockReal.get());
			List<Proveedor> lstProveedores = proveedorService.traerProveedores();

			modelAndView.addObject("orden",orden);
			modelAndView.addObject("proveedores", lstProveedores);
		}
		
		return modelAndView;
	}
	
    @PostMapping("/agregar")
    public RedirectView agregarOrdenDeCompra(@ModelAttribute("orden") OrdenDeCompra ordenDeCompra) {
    	
    	Optional<Proveedor> nuevo = proveedorService.traerProveedor(ordenDeCompra.getProveedor().getIdProveedor());
    	System.out.println(ordenDeCompra.getProveedor().getNombreEmpresa());
    	System.out.println(ordenDeCompra.getStock().getCantidadActual());
    	if (nuevo.isPresent()) {
    		ordenDeCompra.setProveedor(nuevo.get());
    		ordenDeCompra = ordenService.agregarOModificarOrdenDeCompra(ordenDeCompra);
    	}
    	

        return new RedirectView(ViewRouteHelper.ROUTE_INDI_ODC+"/individual/"+ordenDeCompra.getIdOrdenDeCompra()); 
    }

	
}