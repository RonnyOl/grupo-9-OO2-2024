package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Producto;

public interface IProductoService {
	
	
	//AGREGAR O MODIFICAR PRODUCTO
	public boolean agregarOModificarProducto(Producto producto);
		
		//ELIMINAR PRODUCTO
		
		public boolean eliminarProducto(int id);
		
		//HABILITAR O DESHABILITAR PRODUCTO
		
		public boolean cambiarEstadoDeProducto(int id,boolean nuevoEstado);
		
		//TRAER PRODUCTO POR ID CON SU STOCK
		
		public Optional<Producto> traerProducto(int id);
		//TRAER PRODUCTO POR NOMBRE CON SU STOCK
		
		public Optional<Producto> traerProducto(String nombre);
		
		//TRAER LISTA DE PRODUCTOS CON SU STOCK 
		public List<Producto> traerProductos();

}
