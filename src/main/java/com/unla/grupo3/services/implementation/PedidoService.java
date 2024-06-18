package com.unla.grupo3.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.repositories.IPedidoRepository;
import com.unla.grupo3.services.IPedidoService;

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
	
	public List<Pedido> findAllGroupedByProducto() {
	    List<Pedido> listPedidoMatchProductos = new ArrayList<>();
	    List<Pedido> listaPedido = this.traerPedido();
	    for (Pedido pedido : listaPedido) {
	        int idProductoPedido = pedido.getProducto().getIdProducto();
	        boolean encontrado = false; // Booleano para verificar si ya existe el producto en lestPedidoMatchProductos

	        // Verificar si el producto ya está en lestPedidoMatchProductos
	        for (Pedido pedidoExistente : listPedidoMatchProductos) {
	            if (pedidoExistente.getProducto().getIdProducto() == idProductoPedido) {
	                encontrado = true;
	                break;
	            }
	        }

	        // Si no se encontró el producto en lestPedidoMatchProductos, lo agregamos
	        if (!encontrado) {
	        	listPedidoMatchProductos.add(pedido);
	        }
	    }

	    return listPedidoMatchProductos;
	}
	
}