package com.unla.grupo3.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Entity
//@Getter @Setter @NoArgsConstructor
//@Table (name="lote")
public class Lote {
/*
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLote;
	
	@Column (name="fecha_recepcion",nullable=false)
	private LocalDate fechaRecepcion;
	
	//NO SABRIAMOS QUE UTILIZAR EN ESTE CASO, SI GUARDAR O NO EN LA BD ESTE ATRIBUTO
	private OrdenDeCompra ordenDeCompra;
	
	
	@Column(name="cantidad",nullable=false)
	private int cantidad;
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;
	
	
*/	
}
