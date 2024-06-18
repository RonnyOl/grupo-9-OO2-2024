package com.unla.grupo3.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class OrdenDeCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOrdenDeCompra;
	
	@Column(name="cantidad_a_Comprar",nullable=false)
	private int cantidadAComprar;
	
	@CreationTimestamp
	private LocalDate fechaEmision;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;
	
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;
	
	
 	@ManyToOne(optional=false)
	@JoinColumn(name = "proveedor_id", nullable = false)
	private Proveedor proveedor;
 	
 	@Column(name="tiene_lote",nullable=false)
 	private boolean tieneLote; 
 	
	public OrdenDeCompra(int cantidadAComprar, Stock stock, User user,Proveedor proveedor, boolean tieneLote) {
		super();
		this.cantidadAComprar = cantidadAComprar;
		this.stock = stock;
		this.user = user;
		this.proveedor = proveedor;
		this.tieneLote = tieneLote;
	}
	
	public float calcularTotal() {
		return this.stock.getProducto().getCosto()*this.cantidadAComprar;
	}
	
	

	

	
	
}
