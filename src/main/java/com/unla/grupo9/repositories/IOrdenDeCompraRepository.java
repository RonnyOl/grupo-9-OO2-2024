package com.unla.grupo9.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo9.entities.OrdenDeCompra;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.Stock;
import com.unla.grupo9.entities.User;

//Interfaz de OrdenDeCompraRepository
@Repository("ordenDeCompraRepository")
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Serializable> {

	
	//Traer una lista de ordenes de compra por su fecha
	public abstract List<OrdenDeCompra> findAllByfechaEmision(LocalDate nombre);
	
	//Traer un orden de compra creadas por un mismo usuario
	public abstract List<OrdenDeCompra> findByUser(User user);

	
    //Traer un orden de compra creadas por un stock en particular
	public abstract List<OrdenDeCompra> findByStock(Stock stock);
	
	
	////Traer un orden de compra creadas por un mismo usuario y un stock en particular
	public abstract List<OrdenDeCompra> findByUserAndStock(User user, Stock stock);
	
	//Traer una Lista de ordenes de compra que tenga el atributo tiene_lote igual al atributo que se pasa por parametro 
	@Query("SELECT o FROM OrdenDeCompra o  WHERE o.tieneLote = (:estado)")
	public abstract List<OrdenDeCompra> findAllByTieneLote(@Param("estado") boolean estado);
	
	//Devuelve una lista de Stock(asociado a un producto) utilizada para filtrar las ordenes de compra por producto
    @Query("SELECT DISTINCT o.stock FROM OrdenDeCompra o GROUP BY o.stock")
	public abstract List<Stock> findAllDistinctStock();
}

