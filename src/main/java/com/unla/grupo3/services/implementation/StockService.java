package com.unla.grupo3.services.implementation;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.repositories.IStockRepository;
import com.unla.grupo3.services.IStockService;

@Service("stockService")
public class StockService implements IStockService {

    private IStockRepository stockRepository;

    public StockService(IStockRepository StockRepository) {
        this.stockRepository = StockRepository;
    }



    //AGREGAR O MODIFICAR Stock

    public boolean agregarOModificarStock(Stock stock) {
        stockRepository.save(stock);
        return true;
    }

    //ELIMINAR Stock

    public boolean eliminarStock(int id) {
        Optional<Stock> s=stockRepository.findById(id);
        if(s.isPresent()) {
            stockRepository.delete(s.get());
            return true;
        }
        return false;
    }


    //TRAER Stock POR ID 

    public Optional<Stock> traerStock(int id) {
        return stockRepository.findById(id);
    }

    //TRAER Stock POR producto


    public Optional<Stock> traerStock(Producto producto) {
        return stockRepository.findByProducto(producto);
    }
    
    //TRAER LISTA DE STOCK 
    public List<Stock> traerStock(){
        return stockRepository.findAll();
    }





}