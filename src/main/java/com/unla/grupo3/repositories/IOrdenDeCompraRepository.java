package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.entities.User;

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
	
	//Traer una orden de compra que tenga el atributo tiene_lote en false
	@Query("SELECT o FROM OrdenDeCompra o  WHERE o.tieneLote = false")
	public abstract Optional<OrdenDeCompra> findByTieneLote();
	
}

