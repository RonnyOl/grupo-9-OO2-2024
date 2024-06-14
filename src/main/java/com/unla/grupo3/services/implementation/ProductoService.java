package com.unla.grupo3.services.implementation;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.repositories.IProductoRepository;
import com.unla.grupo3.services.IProductoService;

@Service("productoService")
public class ProductoService implements IProductoService {

	private IProductoRepository productoRepository;

	public ProductoService(IProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	//IMPLEMENTAR ABM DE PRODUCTO ADEMAS DE ALGUNOS TRAER BASICOS PARA LA LISTA DE PRODUCTOS Y EL PRODUCTO PARTICULAR
	
	//AGREGAR O MODIFICAR PRODUCTO
	
	public boolean agregarOModificarProducto(Producto producto) {
		productoRepository.save(producto);
		return true;
	}
	
	//ELIMINAR PRODUCTO
	
	public boolean eliminarProducto(int id) {
		Optional<Producto> p=productoRepository.findById(id);
		if(p.isPresent()) {
			productoRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	//HABILITAR O DESHABILITAR PRODUCTO
	
	public boolean cambiarEstadoDeProducto(int id,boolean nuevoEstado) {
		Optional<Producto> p=productoRepository.findById(id);
		if(p.isPresent()) {
			p.get().setHabilitado(nuevoEstado);
			this.agregarOModificarProducto(p.get());
			return true;
		}
		return false;
	}
	
	//TRAER PRODUCTO POR ID CON SU STOCK
	
	public Optional<Producto> traerProducto(int id) {
		return productoRepository.findById(id);
	}
	
	//TRAER PRODUCTO POR NOMBRE CON SU STOCK
	
	public Optional<Producto> traerProducto(String nombre) {
		return productoRepository.findByNombre(nombre);	
	}
	
	//TRAER LISTA DE PRODUCTOS CON SU STOCK 
	public List<Producto> traerProductos(){
		return productoRepository.findAll();
	}
	
	
}