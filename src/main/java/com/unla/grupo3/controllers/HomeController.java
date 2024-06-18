package com.unla.grupo3.controllers;




import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo3.helpers.ViewRouteHelper;



@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDEX);
		return modelAndView;
	}

	@GetMapping("/hello/{name}")
	public ModelAndView helloParams2(@PathVariable("name") String name) {
		ModelAndView mV = new ModelAndView(ViewRouteHelper.HELLO);
		mV.addObject("name", name);
		return mV;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/administrar")
	public ModelAndView administracion() {
		ModelAndView mV = new ModelAndView(ViewRouteHelper.ADM);
		return mV;
	}
	
	@GetMapping("/")
	public RedirectView redirectToHomeIndex() {
		return new RedirectView(ViewRouteHelper.ROUTE);
	}
	

}
