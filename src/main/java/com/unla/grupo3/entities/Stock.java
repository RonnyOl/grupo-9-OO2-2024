package com.unla.grupo3.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "stock", uniqueConstraints = @UniqueConstraint(columnNames = { "id_producto" }))
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idStock;

	@Column(name = "punto_minimo_de_stock", nullable = false)
	private int puntoMinimoDeStock;

	@Column(name = "cantidad_actual", nullable = false)
	private int cantidadActual;

	@Column(name = "reabastecer", nullable = false)
	private boolean reabastecer;

	// STOCK DEBE CREARSE SI O SI CON UN PRODUCTO ASOCIADO
	@OneToOne(optional = false)
	@JoinColumn(name = "id_producto", nullable = false, unique = true)
	private Producto producto;

	public Stock(int puntoMinimoDeStock, int cantidadActual, boolean reabastecer, Producto producto) {
		super();
		this.puntoMinimoDeStock = puntoMinimoDeStock;
		this.cantidadActual = cantidadActual;
		this.reabastecer = reabastecer;
		this.producto = producto;
	}

	
	
}
