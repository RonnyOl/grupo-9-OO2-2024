package com.unla.grupo3.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter @Setter @NoArgsConstructor
@Table (name="producto")
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProducto;
	
	@Column(name="descripcion",nullable=false)
	private String descripcion;
	
	@Column(name="costo",nullable=false)
	private float costo;
	
	@Column(name="precio_de_venta",nullable=false)
	private float precioDeVenta;
	
	@Column(name="nombre",nullable=false)
	private String nombre;
	
	
	@Column(name="habilitado",nullable=false)
	private boolean habilitado;


	public Producto(String descripcion, float costo, float precioDeVenta, String nombre, boolean habilitado) {
		super();
		this.descripcion = descripcion;
		this.costo = costo;
		this.precioDeVenta = precioDeVenta;
		this.nombre = nombre;
		this.habilitado = habilitado;
	}


	
}
