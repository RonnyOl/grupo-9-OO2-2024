package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Lote;



//Interfaz de LoteRepository

@Repository("loteRepository")
public interface ILoteRepositoy extends JpaRepository<Lote, Serializable> {

	
	//Traer Lista de Lotes  por su fecha
	public abstract List<Lote> findAllByfechaRecepcion(LocalDate fecha);
	
	//Traer una Lista de Lotes que tengan el atributo aceptado = al estado que llega por parametro
	@Query("SELECT l FROM Lote l  WHERE l.aceptado = :(estado)")
	public abstract List<Lote> findAllByAceptado(@Param("estado") boolean estado);

	
}
