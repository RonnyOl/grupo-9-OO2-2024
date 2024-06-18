package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Producto;

//Interfaz de ProductoService a implementar
public interface IProductoService {

	// agregar o modificar producto
	public Producto agregarOModificarProducto(Producto producto);

	// eliminar producto
	public boolean eliminarProducto(int id);

	//Valida si el stock asociado tiene la cantidad necesaria para vender
	//Habilita o deshabilita automaticamente el producto segun el caso
	public boolean validarCantidad(Optional<Producto> p);
	
	// habilitar o deshabilitar producto
	public boolean cambiarEstadoDeProducto(Optional<Producto> p, boolean nuevoEstado);

	// traer producto por su id y su stock
	public Optional<Producto> traerProducto(int id);

	// traer producto con su nombre y con su stock
	public Optional<Producto> traerProducto(String nombre);

	// traer lista de productos con su stock
	public List<Producto> traerProductos();
	
}
