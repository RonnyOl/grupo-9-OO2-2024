package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;
import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;

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

}
