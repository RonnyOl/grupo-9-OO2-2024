package com.unla.grupo9.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.unla.grupo9.entities.Lote;



//Interfaz de LoteService a implementar
public interface ILoteService {

	// Agregar o Modificar un Lote
	public Lote agregarOModificarLote(Lote lote);

	// Eliminar un Lote
	public boolean eliminarLote(int id);

	// Traer todos los Lotes de la BD
	public List<Lote> traerLote();

	// Traer Lista de Lotes segun su Fecha de Generacion
	public List<Lote> findAllByfechaRecepcion(LocalDate fecha);

	// Traer un Lote por su ID
	public Optional<Lote> traerLote(int id);

	// Traer una Lista de Lotes que tengan el atributo aceptado = estado
	public List<Lote> traerLote(boolean estado);
	
	// Verificar si existen Ordenes de compra sin Lotes asignados
	public boolean verificarYCrearLote();

	// Modificar el atributo Aceptado
	public boolean cambiarEstadoDeLote(Optional<Lote> l, boolean aceptado);


}
