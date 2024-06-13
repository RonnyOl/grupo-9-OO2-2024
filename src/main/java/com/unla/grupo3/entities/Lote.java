package com.unla.grupo3.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
@Table (name="lote")
public class Lote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLote;
	
	@Column (name="fecha_recepcion",nullable=false)
	private LocalDate fechaRecepcion;
	
	@Column (name="aceptado",nullable=false)
	private boolean aceptado;
	
	
	
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="id_orden_de_compra", nullable=false)
	    private OrdenDeCompra ordenDeCompra;

	public Lote(LocalDate fechaRecepcion, boolean aceptado, OrdenDeCompra ordenDeCompra) {
		super();
		this.fechaRecepcion = fechaRecepcion;
		this.aceptado = aceptado;
		this.ordenDeCompra = ordenDeCompra;
	}
	
	
	 
	
	
	
}
