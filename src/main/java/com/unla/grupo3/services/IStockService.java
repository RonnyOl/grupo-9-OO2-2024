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


}