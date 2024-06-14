package com.unla.grupo3.services.implementation;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.repositories.IOrdenDeCompraRepositoy;


@Service("ordenDeCompraService")
public class OrdenDeCompraService  {

	private IOrdenDeCompraRepositoy OrdenDeCompraRepository;

	public OrdenDeCompraService(IOrdenDeCompraRepositoy OrdenDeCompraRepository) {
		this.OrdenDeCompraRepository = OrdenDeCompraRepository;
	}
	
	//AGREGAR O MODIFICAR ORDEN DE COMPRA
	
	public boolean agregarOModificarOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
		OrdenDeCompraRepository.save(ordenDeCompra);
		return true;
	}
	
	//ELIMINAR ORDEN DE COMPORA
	
	public boolean eliminarOrdenDeCompra(int id) {
		Optional<OrdenDeCompra> p=OrdenDeCompraRepository.findById(id);
		if(p.isPresent()) {
			OrdenDeCompraRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	
	
	//TRAER ORDEN DE COMPRA POR ID CON SU STOCK
	
	public Optional<OrdenDeCompra> traerOrdenDeCompra(int id) {
		return OrdenDeCompraRepository.findById(id);
	}
	
	//TRAER ORDEN DE COMPRA POR ATRIBUTOS CON SU STOCK
	
	public Optional<OrdenDeCompra> traerOrdenDeCompra(LocalDate fecha) {
		return OrdenDeCompraRepository.findByfechaEmision(fecha);	
	}
	
	
	public List<OrdenDeCompra> traerOrdenDeCompra(User user) {
		return OrdenDeCompraRepository.findByUser(user);	
	}
	
	public List<OrdenDeCompra> traerOrdenDeCompra(Stock stock) {
		return OrdenDeCompraRepository.findByStock(stock);	
	}
	
	 //PROBABLEMENTE NO SE USE
	public List<OrdenDeCompra> traerOrdenDeCompra(User user, Stock stock) {
		return OrdenDeCompraRepository.findByUserAndStock(user,stock);	
	}
	
	//TRAER LISTA DE PRODUCTOS CON SU STOCK 
	public List<OrdenDeCompra> traerOrdenDeCompra(){
		return OrdenDeCompraRepository.findAll();
	}
	
	

}