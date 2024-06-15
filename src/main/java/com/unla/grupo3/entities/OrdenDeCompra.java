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


	public OrdenDeCompra(int cantidadAComprar, LocalDate fechaEmision, Stock stock, User user,
			Proveedor proveedor) {
		super();
		this.cantidadAComprar = cantidadAComprar;
		this.fechaEmision = fechaEmision;
		this.stock = stock;
		this.user = user;
		this.proveedor = proveedor;
	}
	
	

	

	
	
}
