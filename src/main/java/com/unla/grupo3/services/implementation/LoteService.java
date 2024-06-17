package com.unla.grupo3.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Lote;
import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.repositories.ILoteRepositoy;
import com.unla.grupo3.services.ILoteService;
import com.unla.grupo3.services.IOrdenDeCompraService;

//Implementacion de la Interfaz ILoteService
@Service("loteService")
public class LoteService implements ILoteService {

	// Vinculacion con Repositorio
	private ILoteRepositoy loteRepository;

	// Vinculacion con otros Servicios
	private IOrdenDeCompraService ordenDeCompraService;

	// Constructor del Servicio
	public LoteService(ILoteRepositoy loteRepository, IOrdenDeCompraService ordenDeCompraService,
			ApplicationEventPublisher eventPublisher) {
		this.loteRepository = loteRepository;
		this.ordenDeCompraService = ordenDeCompraService;
	}

	
	//Getters
	public IOrdenDeCompraService getOrdenDeCompraService() {
		return ordenDeCompraService;
	}
	
	// Agrega o Modifica un Lote
	public Lote agregarOModificarLote(Lote lote) {
		return this.loteRepository.save(lote);
	}

	// Elimina un Lote
	public boolean eliminarLote(int id) {

		boolean borrado = false;

		Optional<Lote> lote = this.loteRepository.findById(id);

		if (lote.isPresent()) {

			this.loteRepository.delete(lote.get());

			borrado = true;
		}

		return borrado;
	}

	/// Trear una lista de todos los Lotes
	public List<Lote> traerLote() {
		return this.loteRepository.findAll();
	}

	/// Traer un Lote por su Fecha de Recepcion
	public Optional<Lote> traerLote(LocalDate fecha) {

		return this.loteRepository.findByfechaRecepcion(fecha);
	}

	/// Traer un Lote por ID
	public Optional<Lote> traerLote(int id) {

		return loteRepository.findById(id);
	}

	// Modificar el estado del atributo de un Lote
	public boolean cambiarEstadoDeLote(Optional<Lote> l, boolean nuevoEstado) {
		boolean cambiado = false;

		if (l.isPresent()) {
			l.get().setAceptado(nuevoEstado);
			this.agregarOModificarLote(l.get());
			cambiado = true;
		}

		return cambiado;
	}

	// Verifica si existen Ordenes de Compra sin Lote asignado
	// Si existe una ODC sin Lote asignado
	// Genera un nuevo Lote y se le asigna la ODC sin Lote
	public boolean verificarYCrearLote() {
		boolean creado = false;

		Optional<OrdenDeCompra> ordenSinLote = ordenDeCompraService.traerOrdenDeCompraSinLote();

		if (ordenSinLote.isPresent()) {
			// Genera un nuevo
			Lote nuevoLote = new Lote(LocalDate.now(), false, ordenSinLote.get());
			// Lo guarda en la BD
			nuevoLote = this.agregarOModificarLote(nuevoLote);

			// Modifica el atributo tieneLote de ODC
			OrdenDeCompra ordenConLote = ordenSinLote.get();
			ordenConLote = ordenDeCompraService.agregarOModificarOrdenDeCompra(ordenConLote);

			creado = true;
		}

		return creado;
	}

	// Devuelve una vista
	public List<Lote> findAllByAceptadoFalse() {
		return loteRepository.findAllByAceptadoFalse();
	}

}