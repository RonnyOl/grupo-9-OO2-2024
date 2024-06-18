package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;


@Repository("pedidoRepository")
public interface IPedidoRepository extends JpaRepository<Pedido, Serializable> {

	
	//Traer un pedido por su producto
	
	public abstract Optional<Pedido> findByProducto (Producto producto);
	
	
	//Traer un pedido por su Usuario 
	
	public abstract Optional<Pedido> findByUser (User user);
	
	//Traer una lista de pedidos hecha por un usuario particular
	
	public abstract List<Pedido> findAllByUser (User user);
	
	public abstract List<Pedido> findAllByProducto (Producto producto);
}
