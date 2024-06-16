package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;

public interface IStockService {

    //AGREGAR O MODIFICAR Stock
    public boolean agregarOModificarStock(Stock stock);

    //ELIMINAR Stock
    public boolean eliminarStock(int id);

    //TRAER Stock POR ID 
    public Optional<Stock> traerStock(int id);

    //TRAER Stock POR producto
    public Optional<Stock> traerStock(Producto producto);

    //TRAER LISTA DE STOCK 
    public List<Stock> traerStock();

    //TRAE UN STOCK QUE NECESITE SER REABASTECIDO
	public Optional<Stock> findByReabastecerTrue();
	
	//VALIDAR SI LA CANTIDAD ACTUAL ES MENOR AL PUNTO MINIMO DE STOCK
	public boolean validarRabastecer(int id);
	
	//CAMBIA EL ESTADO REABASTECER A TRUE/FALSE SEGUN SE NECESITE Y SE LLAMA A LA FUNCION QUE VERIFICA SI EXISTE UN STOCK CON REABASTECER EN TRUE 
	public boolean cambiarEstadoDeReabastecer(int id, boolean estado);
	
	//GENERA UNA ORDEN DE COMPRA A PARTIR DE UN STOCK QUE NECESITE SER REABASTECIDO
	public boolean verificarYGenerarOrdenDeCompra();


}