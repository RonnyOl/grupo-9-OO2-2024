package com.unla.grupo3.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor

public class OrdenDeCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="total", unique=true, nullable=false)
	private int total;

	@Column(name="cantidad_a_Comprar", unique=true, nullable=false)
	private int cantidadAComprar;
	
	@Column(name="fecha_emision", unique=true, nullable=false)
	private LocalDate fechaEmision;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name ="id_producto", nullable = false)
	private Producto producto;
	
	
	public OrdenDeCompra(int total, int cantidadAComprar, LocalDate fechaEmision, Producto producto) {
		super();
		this.total = total;
		this.cantidadAComprar = cantidadAComprar;
		this.fechaEmision = fechaEmision;
		this.producto = producto;
	}
	

	
	
}
