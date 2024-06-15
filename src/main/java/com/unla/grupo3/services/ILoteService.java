package com.unla.grupo3.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Lote;
import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Producto;

public interface ILoteService {

	
    //MODIFICAR
	
	public Lote agregarOModificarLote(Lote lote);
	
	//ELIMINAR ORDEN DE COMPORA
	
	public boolean eliminarLote(int id) ;
	
	///TODOS
	public List<Lote> traerLote();

	///FECHA
	
	public Optional<Lote> traerLote(LocalDate fecha);
	
	///ID
	
	public Optional<Lote> traerLote(int id);

	
	
	//ACEPTAR O NO LOTE
	
		public boolean cambiarEstadoDeLote(Optional<Lote> l,boolean aceptado);
	
	
}
