package com.unla.grupo3.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
	private int id;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="id_producto", nullable=false)
	private Producto producto;
	
	@Column(name="punto_minimo_de_stock",nullable=false)
	private int puntoMinimoDeStock;
	
	@Column(name="cantidad_actual",nullable=false)
	private int cantidadActual;
	
	@Column(name="reabastecer",nullable=false)
	private boolean reabastecer;

	public Stock(Producto producto, int puntoMinimoDeStock, int cantidadActual, boolean reabastecer) {
		super();
		this.producto = producto;
		this.puntoMinimoDeStock = puntoMinimoDeStock;
		this.cantidadActual = cantidadActual;
		this.reabastecer = reabastecer;
	}
	
	
}
