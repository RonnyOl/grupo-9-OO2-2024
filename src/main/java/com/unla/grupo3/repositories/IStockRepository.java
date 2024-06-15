package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;

@Repository("stockRepository")
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

	
	public abstract Optional<Stock> findByProducto (Producto producto);
	
	
}