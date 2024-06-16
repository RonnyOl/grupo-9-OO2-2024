package com.unla.grupo3.services.implementation;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.unla.grupo3.entities.Lote;
import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.repositories.ILoteRepositoy;
import com.unla.grupo3.repositories.IOrdenDeCompraRepository;
import com.unla.grupo3.services.ILoteService;
import com.unla.grupo3.services.IOrdenDeCompraService;



@Service("loteService")
public class LoteService  implements ILoteService {
	
	
	private ILoteRepositoy loteRepository;
	private IOrdenDeCompraService ordenDeCompraService;

	

	public LoteService(ILoteRepositoy loteRepository,IOrdenDeCompraService ordenDeCompraService,ApplicationEventPublisher eventPublisher) {
		this.loteRepository = loteRepository;
		this.ordenDeCompraService=ordenDeCompraService;
	}
	
	
	   //MODIFICAR
	
		public Lote agregarOModificarLote(Lote lote) {
			
			return this.loteRepository.save(lote);
		}
		
		//ELIMINAR ORDEN DE COMPORA
		
		public boolean eliminarLote(int id) {
			
			boolean borrado = false;
			
			Optional<Lote> lote = this.loteRepository.findById(id);
			
			if(lote.isPresent()) {
				
				this.loteRepository.delete(lote.get());	
				
				borrado = true;
			}
			
			return borrado;
		}
		
		///TODOS
		public List<Lote> traerLote(){
		return this.loteRepository.findAll();
		}

		///FECHA
		
		public Optional<Lote> traerLote(LocalDate fecha){
			
			return this.traerLote(fecha);
		}
		
		///ID
		
		public Optional<Lote> traerLote(int id){
			
			
			return loteRepository.findById(id);
		}
		
		
		//ACEPTAR O NO LOTE

		public boolean cambiarEstadoDeLote(Optional<Lote> l,boolean aceptado) {

			if(l.isPresent()) {
				l.get().setAceptado(aceptado);
				this.agregarOModificarLote(l.get());
				return true;
			}
			return false;
		}

		//VERIFICA Y CREA UN LOTE DEPENDIENDO DE SI LA LISTA DE ORDENES DE COMPRA NO VIENE VACIA
		public boolean verificarYCrearLote() {
			boolean creado=false;
			Optional<OrdenDeCompra> ordenSinLote = ordenDeCompraService.traerOrdenDeCompraSinLote();
			
			if (ordenSinLote.isPresent()) {
				//ASIGNA LA PRIMERA DE LA LISTA, COMO EL METODO SE EJECUTA CADA CIERTO TIEMPO, SI EXISTEN MAS SE VAN A IR CREANDO PROGRESIVAMENTE
				ordenSinLote.get().setTieneLote(true);
				OrdenDeCompra ordenConLote = ordenSinLote.get();
				ordenConLote = ordenDeCompraService.agregarOModificarOrdenDeCompra(ordenConLote);
			
				Lote nuevoLote = new Lote(LocalDate.now(),false,ordenConLote);

				nuevoLote=this.agregarOModificarLote(nuevoLote);      
		        creado=true;
			}
			
			return creado;
		}

	
		public List<Lote> findAllByAceptadoFalse(){
			return loteRepository.findAllByAceptadoFalse();
		}
	
	

}