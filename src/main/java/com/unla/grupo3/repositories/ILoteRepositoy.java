package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Lote;



//Interfaz de LoteRepository

@Repository("loteRepository")
public interface ILoteRepositoy extends JpaRepository<Lote, Serializable> {

	
	//Traer un orden de compra por su fecha
	public abstract Optional<Lote> findByfechaRecepcion(LocalDate fecha);
	
	//Traer una Lista de Lotes que tengan el atributo aceptado en False
	@Query("SELECT l FROM Lote l  WHERE l.aceptado = false")
	public abstract List<Lote> findAllByAceptadoFalse();

	
}
