package com.unla.grupo3.services.implementation;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.repositories.IStockRepository;

@Service("stockService")
public class StockService  {

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


    //TRAER Stock POR ID CON SU producto

    public Optional<Stock> traerProducto(int id) {
        return stockRepository.findById(id);
    }


    //TRAER LISTA DE STOCK CON SU PRODUCTO 
    public List<Stock> traerStock(){
        return stockRepository.findAll();
    }





}