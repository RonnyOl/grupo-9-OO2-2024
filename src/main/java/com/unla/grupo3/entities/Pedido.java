package com.unla.grupo3.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table (name="pedido",uniqueConstraints=@UniqueConstraint(columnNames= {"producto_id","user_id"}))
public class Pedido {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPedido;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@ManyToOne(optional=false) 
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name="total",nullable=false)
	private int total;
	
	@Column(name="cantidad",nullable=false)
	private int cantidad;

	 @OneToOne (optional=false)
	 @JoinColumn(name="producto_id", nullable=false)
	 private Producto producto;


	public Pedido(LocalDateTime createdAt, User user, int total, int cantidad, Producto producto) {
		super();
		this.createdAt = createdAt;
		this.user = user;
		this.total = total;
		this.cantidad = cantidad;
		this.producto = producto;
	}
	


}
