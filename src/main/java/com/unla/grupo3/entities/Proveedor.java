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
@Table (name="proveedor")
public class Proveedor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProveedor;
	
	@Column(name="nombre_empresa", nullable=false)
	private String nombreEmpresa;
	
	@Column(name="cuil", nullable=false)
	private String cuil;
	
	@Column(name="direccion", nullable=false)
	private String direccion;

	
	public Proveedor(String nombreEmpresa, String cuil, String direccion) {
		super();
		this.nombreEmpresa = nombreEmpresa;
		this.cuil = cuil;
		this.direccion = direccion;
	}
	
	
	
	
	
}
