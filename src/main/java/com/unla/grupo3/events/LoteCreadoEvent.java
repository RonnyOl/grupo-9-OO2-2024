package com.unla.grupo3.events;

import org.springframework.context.ApplicationEvent;

import com.unla.grupo3.entities.Lote;



@SuppressWarnings("serial")
public class LoteCreadoEvent extends ApplicationEvent {
	
    private final Lote loteCreado;

    public LoteCreadoEvent(Lote loteCreado) {
        super(loteCreado); // Llama al constructor de la superclase con el objeto Lote
        this.loteCreado = loteCreado;
    }

    public Lote getLoteCreado() {
        return loteCreado;
    }

}
