package com.unla.grupo3.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table (name="orden_de_compra",uniqueConstraints=@UniqueConstraint(columnNames= {"stock_id","user_id","proveedor_id"}))
public class OrdenDeCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOrdenDeCompra;
	
	
	@Column(name="total", unique=true, nullable=false)
	private int total;

	@Column(name="cantidad_a_Comprar", unique=true, nullable=false)
	private int cantidadAComprar;
	
	@Column(name="fecha_emision", unique=true, nullable=false)
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


	public OrdenDeCompra(int total, int cantidadAComprar, LocalDate fechaEmision, Stock stock, User user,
			Proveedor proveedor) {
		super();
		this.total = total;
		this.cantidadAComprar = cantidadAComprar;
		this.fechaEmision = fechaEmision;
		this.stock = stock;
		this.user = user;
		this.proveedor = proveedor;
	}
	
	

	

	
	
}
