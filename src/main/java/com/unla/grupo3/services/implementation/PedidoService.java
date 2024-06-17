package com.unla.grupo3.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.repositories.IPedidoRepository;
import com.unla.grupo3.services.IPedidoService;
import com.unla.grupo3.services.IProductoService;

//Implementacion de la Interfaz IPedidoService
@Service("pedidoService")
public class PedidoService implements IPedidoService {


	//Vinculacion con el Repositorio
	private IPedidoRepository pedidoRepository;
	
	//Vinculacion con otros Servicios
	private IProductoService productoService;
	private UserService userService;

	//Constructor de 
	public PedidoService(IPedidoRepository pedidoRepository,UserService userService, IProductoService productoService) {
		this.pedidoRepository = pedidoRepository;
		this.productoService=productoService;
		this.userService=userService;
	}
	
	//Getters
	public IProductoService getProductoService() {
		return productoService;
	}

	public UserService getUserService() {
		return userService;
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

}