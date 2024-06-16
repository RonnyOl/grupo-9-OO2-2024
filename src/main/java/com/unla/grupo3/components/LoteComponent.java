package com.unla.grupo3.components;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unla.grupo3.services.implementation.LoteService;

@Component
public class LoteComponent {
	
	//SE IMPLEMENTA EL SERVICE DE LOTE PARA LLAMAR A LA FUNCION QUE VERIFICA SI CREAR UN NUEVO LOTE CADA CIERTO TIEMPO
	LoteService loteService;
	
	public LoteComponent(LoteService loteService) {
		this.loteService=loteService;
	}
	
	
    @Scheduled(fixedRate = 12000) // Ejecuta cada 2 minutos
	public void verificarSiCrearLote() {
    	System.out.println("Ejecutado");
        loteService.verificarYCrearLote();
	}

}
