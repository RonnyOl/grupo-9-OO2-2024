package com.unla.grupo9.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo9.entities.Pedido;
import com.unla.grupo9.entities.Producto;
import com.unla.grupo9.entities.User;
import com.unla.grupo9.repositories.IPedidoRepository;
import com.unla.grupo9.services.IPedidoService;

//Implementacion de la Interfaz IPedidoService
@Service("pedidoService")
public class PedidoService implements IPedidoService {

	private IPedidoRepository pedidoRepository;

	public PedidoService(IPedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	// agregar o modificar pedido
	public boolean agregarOModificarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
		return true;
	}

	// eliminar pedido
	public boolean eliminarPedido(int id) {
		Optional<Pedido> p = pedidoRepository.findById(id);
		if (p.isPresent()) {
			pedidoRepository.delete(p.get());
			return true;
		}
		return false;
	}

	// traer pedido por id
	public Optional<Pedido> traerPedido(int id) {
		return pedidoRepository.findById(id);
	}

	// traer pedido por producto
	public Optional<Pedido> traerPedido(Producto producto) {
		return pedidoRepository.findByProducto(producto);
	}

	// traer Pedido por Usuario
	public Optional<Pedido> traerPedido(User user) {
		return pedidoRepository.findByUser(user);
	}

	// traer lista de todos los Pedidos
	public List<Pedido> traerPedido() {
		return pedidoRepository.findAll();
	}

	// traer lista de pedidos hechas por un usuario
	public List<Pedido> traerListaPedidoPorUsuario(User user) {
		return pedidoRepository.findAllByUser(user);
	}

	public List<Pedido> traerListaPedidoPorProducto(Producto producto){
		return pedidoRepository.findAllByProducto(producto);
	}
	
	public List<Producto> findAllDistinctPedido(){
		return pedidoRepository.findAllDistinctPedido();
	}
	 public List<Producto> findAllDistinctPedidoByUser(User user){
		 return pedidoRepository.findAllDistinctPedidoByUser(user);
	 }


	public List<Pedido> traerListaPedidoPorUsuarioYProducto(Producto producto,User user) {

		return pedidoRepository.findAllByProductoAndUser(producto, user);
	}
	 
	
}