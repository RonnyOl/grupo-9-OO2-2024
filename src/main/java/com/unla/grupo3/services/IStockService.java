package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;

//Interfaz a implementar de StockService
public interface IStockService {

	// Agrega o Modifica un Stock
	public boolean agregarOModificarStock(Stock stock);

	// Elimina un Stock
	public boolean eliminarStock(int id);
	
	//Modificar y Sumar Stock 
	public boolean sumarStock(Stock s, int cantidad);
	
	//Modificar y Restar Stock 
	public boolean restarStock(Stock s, int cantidad);

	// Trae un Stock por su ID
	public Optional<Stock> traerStock(int id);

	// Trae un Stock por su Producto
	public Optional<Stock> traerStock(Producto producto);

	// Trae Lista de todos los Stock
	public List<Stock> traerStock();

	// Trae una Lista de Stocks con su atributo reabastecer = estado
	public List<Stock> traerStock(boolean estado );

	// Valida si el Stock asociado al ID enviado tiene CantidadActual <= PuntoMinimo
	public boolean validarRabastecer(int id);

	// Modifica el estado del atributo Reabastecer
	public boolean cambiarEstadoDeReabastecer(int id, boolean estado);

	// Verifica si hay un Stock que necesite ser Reabastecido
	// Si existe genera una OrdenDeCompra asociada a ese Stock para reabastecer el
	// Producto neceasario
	public boolean verificarYGenerarOrdenDeCompra();

}