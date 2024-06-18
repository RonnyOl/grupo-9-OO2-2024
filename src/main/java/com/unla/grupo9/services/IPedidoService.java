package com.unla.grupo9.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo9.entities.Pedido;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.User;

//Interfaz de PedidoService a implementar
public interface IPedidoService {

	// agregar o mofidicar pedido
	public boolean agregarOModificarPedido(Pedido pedido);

	// eliminar Pedido
	public boolean eliminarPedido(int id);

	// traer pedido por id
	public Optional<Pedido> traerPedido(int id);

	// trae pedido por producto
	public Optional<Pedido> traerPedido(Producto producto);

	// traer pedido por usuario
	public Optional<Pedido> traerPedido(User user);

	// traer lista de todos los Pedido
	public List<Pedido> traerPedido();

	// traer lista de los pedidos de un solo usuario
	public List<Pedido> traerListaPedidoPorUsuario(User user);
	// traer lista de los pedidos por un producto
	public List<Pedido> traerListaPedidoPorProducto(Producto producto);
	// Trae una lista de productos no repetidos de los distintos pedidos
	 public List<Producto> findAllDistinctPedido();
	 
	 public List<Producto> findAllDistinctPedidoByUser(User user);
}
