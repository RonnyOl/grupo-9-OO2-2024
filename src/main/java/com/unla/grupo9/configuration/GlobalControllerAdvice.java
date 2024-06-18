package com.unla.grupo9.configuration;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalControllerAdvice {

	
	//Envia a todas las vistas el usuario que se autentifico para hacer las validaciones correspondientes
	//Se realiza de esta manera para no repetir siempre el codigo que envia el usuario a la vista
	 @ModelAttribute("user")
	    public UserDetails addUserRoleToModel() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated() && 
	                !authentication.getName().equals("anonymousUser")) {
	            return (UserDetails) authentication.getPrincipal();
	        }
	        return null;
	    }

}