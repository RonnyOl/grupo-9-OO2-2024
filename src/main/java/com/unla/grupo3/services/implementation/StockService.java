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

@Service("stockService")
public class StockService implements IStockService {

	private IStockRepository stockRepository;
	private IOrdenDeCompraService ordenDeCompraService;
	private IProveedorService proveedorService;
	private ILoteService loteService;


	public StockService(IStockRepository StockRepository, IOrdenDeCompraService ordendDeCompraService,
			IProveedorService proveedorService,ILoteService loteService) {
		this.stockRepository = StockRepository;
		this.ordenDeCompraService = ordendDeCompraService;
		this.proveedorService = proveedorService;
		this.loteService=loteService;
	}

	// AGREGAR O MODIFICAR Stock

	public boolean agregarOModificarStock(Stock stock) {
		stockRepository.save(stock);
		return true;
	}

	// ELIMINAR Stock

	public boolean eliminarStock(int id) {
		Optional<Stock> s = stockRepository.findById(id);
		if (s.isPresent()) {
			stockRepository.delete(s.get());
			return true;
		}
		return false;
	}

	// TRAER Stock POR ID

	public Optional<Stock> traerStock(int id) {
		return stockRepository.findById(id);
	}

	// TRAER Stock POR producto
	public Optional<Stock> traerStock(Producto producto) {
		return stockRepository.findByProducto(producto);
	}

	// TRAER LISTA DE STOCK
	public List<Stock> traerStock() {
		return stockRepository.findAll();
	}

	// TRAE UN STOCK QUE NECESITE SER REABASTECIDO
	public Optional<Stock> findByReabastecerTrue() {
		return stockRepository.findByReabastecerTrue();
	};

	// VALIDAR SI LA CANTIDAD ACTUAL ES MENOR AL PUNTO MINIMO DE STOCK
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

	// CAMBIA EL ESTADO REABASTECER A TRUE/FALSE SEGUN SE NECESITE Y SE LLAMA A LA
	// FUNCION QUE VERIFICA SI EXISTE UN STOCK CON REABASTECER EN TRUE
	public boolean cambiarEstadoDeReabastecer(int id, boolean estado) {
		boolean cambio = false;
		Optional<Stock> stock = this.traerStock(id);

		if (stock.isPresent()) {
			stock.get().setReabastecer(estado);
			this.agregarOModificarStock(stock.get());
			cambio = true;
		}

		if (estado == true)
			this.verificarYGenerarOrdenDeCompra();

		return cambio;
	}

	// GENERA UNA ORDEN DE COMPRA A PARTIR DE UN STOCK QUE NECESITE SER REABASTECIDO
	public boolean verificarYGenerarOrdenDeCompra() {
		Random rand = new Random();
		boolean generada = false;

		Optional<Stock> stockAReabastecer = this.findByReabastecerTrue();

		if (stockAReabastecer.isPresent()) {
			// LA CANTIDAD A COMPRAR ES IGUAL AL PUNTO MINIMO DE STOCK DETERMINADO PARA ESE
			// PRODUCTO,
			int cantidadAComprar = stockAReabastecer.get().getPuntoMinimoDeStock(); // ASI SE SABE AL 100 QUE SATISFACE
																					// LA CONDICION

			// SE TRAEN LOS PROVEEDORES EXISTENTES EN LA BD Y SE LE ASIGNA UNO ALEATORIO A
			// LA ORDEN DE COMPRA
			List<Proveedor> lstProveedores = proveedorService.traerProveedores();
			int nro = rand.nextInt(1, lstProveedores.size());
			Proveedor proveedorAsignado = lstProveedores.get(nro);

			// SE GENERA LA NUEVA ORDEN Y SE GUARDA EN LA BD
			OrdenDeCompra nuevaOrden = new OrdenDeCompra(cantidadAComprar, stockAReabastecer.get(), null,
					proveedorAsignado, false);
			nuevaOrden = ordenDeCompraService.agregarOModificarOrdenDeCompra(nuevaOrden);
			
			//VERIFICA SI SE DEBE GENERAR UN LOTE A PARTIR DE ESTA NUEVA ORDEN DE COMPRA GENERADA
			loteService.verificarYCrearLote();
			
			generada = true;
		}
		return generada;
	}

}