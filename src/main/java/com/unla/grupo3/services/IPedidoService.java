package com.unla.grupo3.services;


import java.util.List;
import java.util.Optional;
import com.unla.grupo3.entities.Pedido;
import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.User;



public interface IPedidoService {

	
	//IMPLEMENTAR ABM DE Pedido ADEMAS DE ALGUNOS TRAER BASICOS PARA LA LISTA DE Pedido 
	
	//AGREGAR O MODIFICAR Pedido
	
	public boolean agregarOModificarPedido(Pedido pedido);
	
	
	//ELIMINAR Pedido
	
	public boolean eliminarPedido(int id);
	
	//TRAER Pedido POR ID 
	
	public Optional<Pedido> traerPedido(int id);
	
	//TRAER Pedido POR Producto
	
	public Optional<Pedido> traerPedido(Producto producto);
	//TRAER Pedido POR Usuario
	
	public Optional<Pedido> traerPedido(User user);
	
	//TRAER LISTA DE Pedido 
	public List<Pedido> traerPedido();
	
	//TRAER LISTA DE PEDIDOS POR SU USUARIO
	public List<Pedido> traerListaPedidoPorUsuario(User user);
	

	
	
}
