package com.unla.grupo9.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.grupo9.entities.Producto;


//Interfaz de Repositorio de Producto
@Repository("productoRepository")
public interface IProductoRepository extends JpaRepository<Producto, Serializable> {

	
	//Traer un producto por su nombre con su Stock
	public abstract Optional<Producto> findByNombre(String nombre);
	
}
