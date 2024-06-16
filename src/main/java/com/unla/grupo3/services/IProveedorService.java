package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Proveedor;


public interface IProveedorService {
	
	// toma un proveedor y lo agrega 
		public Proveedor agregarModificarProveedor(Proveedor proveedor);
		
		//Busca y elimina a un proveedor por id
		public boolean eliminarProveedor(int id);
		
		//trae un proveedor por id
		public Optional<Proveedor> traerProveedor(int id);
		
		//Trae por nombre empresa
		public Optional<Proveedor> traerProveedor(String nombreEmpresa);
		
		//Trae por nombre empresa y cuil
		public Optional<Proveedor> traerProveedor(String nombreEmpresa, String cuil);
		
		//trae todos los proveedores
		public List<Proveedor> traerProveedores();
		
		
}
