package com.unla.grupo3.entities;

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
@Table (name="stock")
public class Stock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStock;

	
	@Column(name="punto_minimo_de_stock",nullable=false)
	private int puntoMinimoDeStock;
	
	@Column(name="cantidad_actual",nullable=false)
	private int cantidadActual;
	
	@Column(name="reabastecer",nullable=false)
	private boolean reabastecer;
	
	@OneToOne
    @JoinColumn(name = "id_producto")
	private Producto producto;

	public Stock(int puntoMinimoDeStock, int cantidadActual, boolean reabastecer, Producto producto) {
		super();
		this.puntoMinimoDeStock = puntoMinimoDeStock;
		this.cantidadActual = cantidadActual;
		this.reabastecer = reabastecer;
		this.producto = producto;
	}

	 
	 
}
