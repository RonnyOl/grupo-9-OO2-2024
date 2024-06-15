package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Lote;




@Repository("loteRepository")
public interface ILoteRepositoy extends JpaRepository<Lote, Serializable> {

	
	//Traer un orden de compra por su fecha
	public abstract Optional<Lote> findByfechaRecepcion(LocalDate fecha);
	
	
	//traerPorID
	
	public abstract Optional<Lote> findByIdLote(int id);
	
}
