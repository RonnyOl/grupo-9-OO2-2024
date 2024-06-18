package com.unla.grupo9.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.unla.grupo9.entities.OrdenDeCompra;
import com.unla.grupo9.entities.Stock;
import com.unla.grupo9.entities.User;

//Interfaz a implementar de OrdenDeCompraService
public interface IOrdenDeCompraService {

	// Agrega o Modifica una Orden De Compra
	public OrdenDeCompra agregarOModificarOrdenDeCompra(OrdenDeCompra ordenDeCompra);

	// Elimina una Orden De Compra
	public boolean eliminarOrdenDeCompra(int id);

	// Modificar atributo tieneLote
	public boolean cambiarEstadoTieneLote(Optional<OrdenDeCompra> orden, boolean nuevoEstado);

	// Traer Orden De Compra por ID
	public Optional<OrdenDeCompra> traerOrdenDeCompra(int id);

	// Treaer Lista de Ordenes de Compra por su Fecha de Emision
	public List<OrdenDeCompra> traerOrdenDeCompra(LocalDate fecha);

	// Traer Lista de Ordenes de Compra por su User
	public List<OrdenDeCompra> traerOrdenDeCompra(User user);

	// Traer Lista de Ordenes de Compra por su Stock
	public List<OrdenDeCompra> traerOrdenDeCompra(Stock stock);

	// Traer Lista de Ordenes de Compra por su User y Stock
	public List<OrdenDeCompra> traerOrdenDeCompra(User user, Stock stock);

	// Traer Lista de Ordenes de Compra
	public List<OrdenDeCompra> traerOrdenDeCompra();

	// Traer una Lista de Ordenes de Compra con su atributo tieneLote = estado
	public List<OrdenDeCompra> traerOrdenDeCompra(boolean estado);

	//Devuelve una lista de Stock(asociado a un producto) utilizada para filtrar las ordenes de compra por producto
	public  List<Stock> findAllDistinctStock();
}