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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table (name="pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	

	
	@Column(name="total",nullable=false)
	private int total;
	
	public Pedido(LocalDateTime createdAt, int total) {
		super();
		this.createdAt = createdAt;
		this.total = total;
	}

}
