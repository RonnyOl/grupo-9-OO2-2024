package com.unla.grupo3.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.entities.User;
import com.unla.grupo3.repositories.IOrdenDeCompraRepository;
import com.unla.grupo3.services.IOrdenDeCompraService;

//Implementacion de la Interfaz IOrdenDeCompraService
@Service("ordenDeCompraService")
public class OrdenDeCompraService implements IOrdenDeCompraService {

	// Vinculacion con Repositorio
	private IOrdenDeCompraRepository OrdenDeCompraRepository;

	// Constructor del Servicio
	public OrdenDeCompraService(IOrdenDeCompraRepository OrdenDeCompraRepository) {
		this.OrdenDeCompraRepository = OrdenDeCompraRepository;
	}

	// Agrega o Modifica una Orden De Compra
	public OrdenDeCompra agregarOModificarOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
		return OrdenDeCompraRepository.save(ordenDeCompra);
	}

	// Elimina una Orden De Compra
	public boolean eliminarOrdenDeCompra(int id) {

		
		Optional<OrdenDeCompra> p = OrdenDeCompraRepository.findById(id);
		if (p.isPresent()) {
			OrdenDeCompraRepository.delete(p.get());
			return true;
		}
		return false;
	}


	//Modificar atributo tieneLote
	public boolean cambiarEstadoTieneLote(Optional<OrdenDeCompra> orden, boolean nuevoEstado) {
		boolean cambiado=false;
		
		if(orden.isPresent()) {
			orden.get().setTieneLote(nuevoEstado);
			this.agregarOModificarOrdenDeCompra(orden.get());
			cambiado = true;
		}
		return cambiado;
	}
	
	
	// Traer Orden De Compra por ID
	public Optional<OrdenDeCompra> traerOrdenDeCompra(int id) {
		return OrdenDeCompraRepository.findById(id);
	}

	// Traer Lista de Ordenes de Compra por su Fecha de Emision
	public List<OrdenDeCompra> traerOrdenDeCompra(LocalDate fecha) {
		return OrdenDeCompraRepository.findAllByfechaEmision(fecha);
	}

	// Traer Lista de Ordenes de Compra por su User
	public List<OrdenDeCompra> traerOrdenDeCompra(User user) {
		return OrdenDeCompraRepository.findByUser(user);
	}

	// Traer Lista de Ordenes de Compra por su Stock
	public List<OrdenDeCompra> traerOrdenDeCompra(Stock stock) {
		return OrdenDeCompraRepository.findByStock(stock);
	}

	// Traer Lista de Ordenes de Compra por su User y Stock
	public List<OrdenDeCompra> traerOrdenDeCompra(User user, Stock stock) {
		return OrdenDeCompraRepository.findByUserAndStock(user, stock);
	}

	// Traer Lista de Ordenes de Compra
	public List<OrdenDeCompra> traerOrdenDeCompra() {
		return OrdenDeCompraRepository.findAll();
	}

	// Traer una Lista de Ordenes de Compra con su atributo tieneLote = estado
	public List<OrdenDeCompra> traerOrdenDeCompra(boolean estado) {
		return OrdenDeCompraRepository.findAllByTieneLote(estado);
	}
	
	

}