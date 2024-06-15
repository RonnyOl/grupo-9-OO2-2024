package com.unla.grupo3.services.implementation;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.unla.grupo3.entities.Lote;
import com.unla.grupo3.repositories.ILoteRepositoy;
import com.unla.grupo3.services.ILoteService;



@Service("loteService")
public class LoteService  implements ILoteService {
	
	
	private ILoteRepositoy loteRepositoy;

	
	

	public LoteService(ILoteRepositoy loteRepository) {
		this.loteRepositoy = loteRepository;
	}
	
	
	   //MODIFICAR
	
		public Lote agregarOModificarLote(Lote lote) {
			
			return this.loteRepositoy.save(lote);
		}
		
		//ELIMINAR ORDEN DE COMPORA
		
		public boolean eliminarLote(int id) {
			
			boolean borrado = false;
			
			Optional<Lote> lote = this.loteRepositoy.findByIdLote(id);
			
			if(lote.isPresent()) {
				
				this.loteRepositoy.delete(lote.get());	
				
				borrado = true;
			}
			
			return borrado;
		}
		
		///TODOS
		public List<Lote> traerLote(){
		return this.loteRepositoy.findAll();
		}

		///FECHA
		
		public Optional<Lote> traerLote(LocalDate fecha){
			
			return this.traerLote(fecha);
		}
		
		///ID
		
		public Optional<Lote> traerLote(int id){
			
			return this.traerLote(id);
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
	
	
	

}