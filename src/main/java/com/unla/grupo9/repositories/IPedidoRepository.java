package com.unla.grupo9.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo9.entities.Pedido;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.User;


@Repository("pedidoRepository")
public interface IPedidoRepository extends JpaRepository<Pedido, Serializable> {

	
	//Traer un pedido por su producto	
	public abstract Optional<Pedido> findByProducto (Producto producto);
	
	//Traer un pedido por su Usuario 
	public abstract Optional<Pedido> findByUser (User user);
	
	//Traer una lista de pedidos hecha por un usuario particular
	public abstract List<Pedido> findAllByUser (User user);
	
	public abstract List<Pedido> findAllByProducto (Producto producto);

	public abstract List<Pedido> findAllByProductoAndUser (Producto producto, User user);
	
	//Devuelve una lista de Pedido(asociado a un producto) utilizada para filtrar los pedidos por producto
    @Query("SELECT DISTINCT p.producto FROM Pedido p GROUP BY p.producto")
    public abstract List<Producto> findAllDistinctPedido();
    
 // Devuelve una lista de productos distintos asociados a un pedido y filtrados por usuario
    @Query("SELECT DISTINCT p.producto FROM Pedido p WHERE p.user = (:user)")
    public abstract List<Producto> findAllDistinctPedidoByUser(@Param("user") User usuario);

}

