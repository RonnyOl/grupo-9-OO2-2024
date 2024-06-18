package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;


//Interfaz de StockRepository
@Repository("stockRepository")
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

	//Devuelve un Stock filtrado por su Producto
	public abstract Optional<Stock> findByProducto (Producto producto);
	
	//Devuelve un Stock donde su atributo reabastecer = estado enviado por parametro
	@Query("SELECT s FROM Stock s  WHERE s.reabastecer = (:estado)")
	public abstract List<Stock> findAllByReabastecer(@Param("estado") boolean estado);
}