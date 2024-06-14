package com.unla.grupo3.services.implementation;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.repositories.IPedidoRepository;

@Service("pedidoService")
public class PedidoService  {

	private IPedidoRepository pedidoRepository;

	public PedidoService(IPedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	//IMPLEMENTAR ABM DE Pedido ADEMAS DE ALGUNOS TRAER BASICOS PARA LA LISTA DE Pedido 
	
	//AGREGAR O MODIFICAR Pedido
	
	public boolean agregarOModificarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
		return true;
	}
	
	//ELIMINAR Pedido
	
	public boolean eliminarPedido(int id) {
		Optional<Pedido> p=pedidoRepository.findById(id);
		if(p.isPresent()) {
			pedidoRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	//TRAER Pedido POR ID 
	
	public Optional<Pedido> traerPedido(int id) {
		return pedidoRepository.findById(id);
	}
	
	//TRAER Pedido POR Producto
	
	public Optional<Pedido> traerPedido(Producto producto) {
		return pedidoRepository.findByProducto(producto);	
	}
	
	//TRAER Pedido POR Usuario
	
	public Optional<Pedido> traerPedido(User user) {
		return pedidoRepository.findByUser(user);	
	}
	
	//TRAER LISTA DE Pedido 
	public List<Pedido> traerPedido(){
		return pedidoRepository.findAll();
	}
	
	//TRAER LISTA DE PEDIDOS POR SU USUARIO
	public List<Pedido> traerListaPedidoPorUsuario(User user){
		return pedidoRepository.findAllByUser(user);
	}
	

	
}