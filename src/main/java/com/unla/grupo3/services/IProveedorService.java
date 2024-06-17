package com.unla.grupo3.services;

import java.util.List;
import java.util.Optional;

import com.unla.grupo3.entities.Proveedor;

//Interfaz a implementar de ProveedorService
public interface IProveedorService {
	
	
		
		//Agrega o Modifica un Proveedor
		public Proveedor agregarModificarProveedor(Proveedor proveedor);
		
		//Elimina un Proveedor
		public boolean eliminarProveedor(int id);
		
		//Trae un Proveedor por id
		public Optional<Proveedor> traerProveedor(int id);
		
		//Trae un Proveedor por nombreEmpresa
		public Optional<Proveedor> traerProveedor(String nombreEmpresa);
		
		//Trae un Proveedor por nombreEmpresa y Cuil
		public Optional<Proveedor> traerProveedor(String nombreEmpresa, String cuil);
		
		//Trae Lista de Proveedores
		public List<Proveedor> traerProveedores();
		
		
}
