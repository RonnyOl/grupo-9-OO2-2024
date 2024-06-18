package com.unla.grupo3.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejador para AccessDeniedException (Acceso denegado)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // Código de respuesta HTTP 403
    public String handleAccessDeniedException(AccessDeniedException ex) {
  
        return "errores/403"; // Nombre de la vista (sin extensión .html)
    }
}