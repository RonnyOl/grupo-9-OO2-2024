package com.unla.grupo3.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Proveedor;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.repositories.IStockRepository;
import com.unla.grupo3.services.ILoteService;
import com.unla.grupo3.services.IOrdenDeCompraService;
import com.unla.grupo3.services.IProveedorService;
import com.unla.grupo3.services.IStockService;

//Implementacion de la Interfaz IStockService 
@Service("stockService")
public class StockService implements IStockService {

	// Vinculacion con el Repositorio
	private IStockRepository stockRepository;

	// Vinculacion con otros Servicios
	private IOrdenDeCompraService ordenDeCompraService;
	private IProveedorService proveedorService;
	private ILoteService loteService;

	// Constructor de StockService
	public StockService(IStockRepository StockRepository, IOrdenDeCompraService ordendDeCompraService,
			IProveedorService proveedorService, ILoteService loteService) {
		this.stockRepository = StockRepository;
		this.ordenDeCompraService = ordendDeCompraService;
		this.proveedorService = proveedorService;
		this.loteService = loteService;
	}

	// Agrega o Modifica un Stock
	public boolean agregarOModificarStock(Stock stock) {
		stockRepository.save(stock);
		return true;
	}

	// Elimina un Stock
	public boolean eliminarStock(int id) {
		Optional<Stock> s = stockRepository.findById(id);
		if (s.isPresent()) {
			stockRepository.delete(s.get());
			return true;
		}
		return false;
	}
	
	//Modificar y Sumar Stock 
	public boolean sumarStock(Stock s, int cantidad) {
		boolean sumado=false;
		
		if(s != null) {
			s.setCantidadActual(s.getCantidadActual()+cantidad);
			sumado = true;
		}
		
		return sumado;
	}
	
	//Modificar y Restar Stock 
	public boolean restarStock(Stock s, int cantidad) {
		boolean restado=false;
		
		if(s != null) {
			s.setCantidadActual(s.getCantidadActual()-cantidad);
			restado = true;
		}
		
		return restado;
	}

	// Trae un Stock por su ID
	public Optional<Stock> traerStock(int id) {
		return stockRepository.findById(id);
	}

	// Trae un Stock por su Producto
	public Optional<Stock> traerStock(Producto producto) {
		return stockRepository.findByProducto(producto);
	}

	// Trae Lista de todos los Stock
	public List<Stock> traerStock() {
		return stockRepository.findAll();
	}

	// Trae un Stock con su atributo reabastecer=True
	public Optional<Stock> findByReabastecerTrue() {
		return stockRepository.findByReabastecerTrue();
	};

	// Valida si el Stock asociado al ID enviado tiene CantidadActual <= PuntoMinimo
	public boolean validarRabastecer(int id) {

		Optional<Stock> stock = this.traerStock(id);
		boolean reabastecer = false;

		if (stock.isPresent()) {
			if ((stock.get().getCantidadActual() <= stock.get().getPuntoMinimoDeStock())) {
				reabastecer = true;
			} else {
				reabastecer = false;
			}
		}
		return this.cambiarEstadoDeReabastecer(id, reabastecer);

	}

	// Modifica el estado del atributo Reabastecer
	public boolean cambiarEstadoDeReabastecer(int id, boolean estado) {
		boolean cambio = false;
		Optional<Stock> stock = this.traerStock(id);

		
		if (stock.isPresent()) {
			stock.get().setReabastecer(estado);
			this.agregarOModificarStock(stock.get());
			cambio = true;
		}

		if (estado == true)
			//Se llama a funcion para verificar si ahora existe un Stock a reabastecer
			this.verificarYGenerarOrdenDeCompra();

		return cambio;
	}

	// Verifica si hay un Stock que necesite ser Reabastecido
	// Si existe genera una OrdenDeCompra asociada a ese Stock para reabastecer el
	// Producto neceasario
	public boolean verificarYGenerarOrdenDeCompra() {
		Random rand = new Random();
		boolean generada = false;

		Optional<Stock> stockAReabastecer = this.findByReabastecerTrue();

		if (stockAReabastecer.isPresent()) {
			//Se asigna automaticamente la cantidad a comprar a partir del Punto Minimo
			int cantidadAComprar = stockAReabastecer.get().getPuntoMinimoDeStock(); 

			// Se asigna un Proveedor aleatorio a la Orden de Compra
			List<Proveedor> lstProveedores = proveedorService.traerProveedores();
			int nro = rand.nextInt(1, lstProveedores.size());
			Proveedor proveedorAsignado = lstProveedores.get(nro);

			// Se genera una nueva Orden que se guarda en la BD
			OrdenDeCompra nuevaOrden = new OrdenDeCompra(cantidadAComprar, stockAReabastecer.get(), null,
					proveedorAsignado, false);
			nuevaOrden = ordenDeCompraService.agregarOModificarOrdenDeCompra(nuevaOrden);

			// Se llama al metodo para verificar si se debe generar un Lote
			loteService.verificarYCrearLote();

			generada = true;
		}
		return generada;
	}

}