package com.unla.grupo9.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.unla.grupo9.entities.OrdenDeCompra;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.Proveedor;
import com.unla.grupo9.entities.Stock;
import com.unla.grupo9.repositories.IStockRepository;
import com.unla.grupo9.services.ILoteService;
import com.unla.grupo9.services.IOrdenDeCompraService;
import com.unla.grupo9.services.IProveedorService;
import com.unla.grupo9.services.IStockService;

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
			this.agregarOModificarStock(s);
			sumado = true;
		}
		
		return sumado;
	}
	
	//Modificar y Restar Stock 
	public boolean restarStock(Stock s, int cantidad) {
		boolean restado=false;
		
		if(s != null) {
			s.setCantidadActual(s.getCantidadActual()-cantidad);
			this.agregarOModificarStock(s);

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

	// Trae una Lista de Stocks con su atributo reabastecer=estado<
	public List<Stock> traerStock(boolean estado) {
		return stockRepository.findAllByReabastecer( estado);
	};

	// Valida si el Stock asociado al ID enviado tiene CantidadActual <= PuntoMinimo
	public boolean validarRabastecer(int id) {

		Optional<Stock> stock = this.traerStock(id);
		boolean anteriorValor=stock.get().isReabastecer();
		boolean reabastecer;
		boolean cambio=false;
		
		if (stock.isPresent()) {
			if ((stock.get().getCantidadActual() <= stock.get().getPuntoMinimoDeStock())) {
				reabastecer = true;
			} else {
				reabastecer = false;
			}
			
			if (anteriorValor != reabastecer) {
				cambio= this.cambiarEstadoDeReabastecer(id, reabastecer);
				
			}
		}
		
		return cambio;

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

		if (estado == true) {
			//Se llama a funcion para verificar si ahora existe un Stock a reabastecer
			this.verificarYGenerarOrdenDeCompra();
		}
		
		return cambio;
	}

	// Verifica si hay un Stock que necesite ser Reabastecido
	// Si existe genera una OrdenDeCompra asociada a ese Stock para reabastecer el
	// Producto neceasario
	public boolean verificarYGenerarOrdenDeCompra() {
		Random rand = new Random();
		boolean generada = false;

		List<Stock> stocksAReabastecer = this.traerStock(true);

		//Mientras que la lista no este vacia, se trabaja con el primer elemento
		while (stocksAReabastecer.isEmpty()) {
			//Se asigna automaticamente la cantidad a comprar a partir del Punto Minimo
			int cantidadAComprar = stocksAReabastecer.get(0).getPuntoMinimoDeStock(); 

			// Se asigna un Proveedor aleatorio a la Orden de Compra
			List<Proveedor> lstProveedores = proveedorService.traerProveedores();
			int nro = rand.nextInt(1, lstProveedores.size());
			Proveedor proveedorAsignado = lstProveedores.get(nro);

			// Se genera una nueva Orden que se guarda en la BD
			OrdenDeCompra nuevaOrden = new OrdenDeCompra(cantidadAComprar, stocksAReabastecer.get(0), null,
					proveedorAsignado, false);
			nuevaOrden = ordenDeCompraService.agregarOModificarOrdenDeCompra(nuevaOrden);

			// Se llama al metodo para verificar si se debe generar un Lote
			loteService.verificarYCrearLote();

			//Se elimina el stock de la lista  
			stocksAReabastecer.remove(0);
			
			generada = true;
		}
		
		
		return generada;
	}



}