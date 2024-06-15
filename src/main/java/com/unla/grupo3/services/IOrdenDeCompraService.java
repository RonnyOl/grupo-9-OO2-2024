package com.unla.grupo3.services;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.OrdenDeCompra;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.entities.User;



public interface IOrdenDeCompraService {


	//AGREGAR O MODIFICAR ORDEN DE COMPRA
	
	public OrdenDeCompra agregarOModificarOrdenDeCompra(OrdenDeCompra ordenDeCompra);
	//ELIMINAR ORDEN DE COMPORA
	
	public boolean eliminarOrdenDeCompra(int id) ;
	
	//TRAER ORDEN DE COMPRA POR ID CON SU STOCK
	
	public Optional<OrdenDeCompra> traerOrdenDeCompra(int id);
	
	//TRAER ORDEN DE COMPRA POR ATRIBUTOS CON SU STOCK
	
	public Optional<OrdenDeCompra> traerOrdenDeCompra(LocalDate fecha) ;
	
	public List<OrdenDeCompra> traerOrdenDeCompra(User user);
	
	public List<OrdenDeCompra> traerOrdenDeCompra(Stock stock);
	
	 //PROBABLEMENTE NO SE USE
	public List<OrdenDeCompra> traerOrdenDeCompra(User user, Stock stock);
	
	//TRAER LISTA DE PRODUCTOS CON SU STOCK 
	public List<OrdenDeCompra> traerOrdenDeCompra();
	
	
	


}