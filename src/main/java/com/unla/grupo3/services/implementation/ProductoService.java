package com.unla.grupo3.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.repositories.IProductoRepository;
import com.unla.grupo3.services.IProductoService;
import com.unla.grupo3.services.IStockService;

//Implementacion de la Interfaz IProductoService
@Service("productoService")
public class ProductoService implements IProductoService {


	//Vinculacion del Repositorio
	private IProductoRepository productoRepository;

	//Vinculacion con otros Servicios
	private IStockService stockService;
	
	//Constructor del Service
	public ProductoService(IProductoRepository productoRepository,IStockService stockService) {
		this.productoRepository = productoRepository;
		this.stockService=stockService;
	}
	
	//Getters

	public IStockService getStockService() {
		return stockService;
	}

	// agregar o modificar un producto
	public Producto agregarOModificarProducto(Producto producto) {
		return productoRepository.save(producto);

	}

	// eliminar producto
	public boolean eliminarProducto(int id) {
		Optional<Producto> p = productoRepository.findById(id);
		if (p.isPresent()) {
			productoRepository.delete(p.get());
			return true;
		}
		return false;
	}

	// habilitar o des habilitar producto
	public boolean cambiarEstadoDeProducto(Optional<Producto> p, boolean nuevoEstado) {

		if (p.isPresent()) {
			p.get().setHabilitado(nuevoEstado);
			this.agregarOModificarProducto(p.get());
			return true;
		}
		return false;
	}

	// traer producto por su id con su stock
	public Optional<Producto> traerProducto(int id) {
		return productoRepository.findById(id);
	}

	// traer producto por nombre y su stock
	public Optional<Producto> traerProducto(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	// traer lista de productos con su stock
	public List<Producto> traerProductos() {
		return productoRepository.findAll();
	}

}