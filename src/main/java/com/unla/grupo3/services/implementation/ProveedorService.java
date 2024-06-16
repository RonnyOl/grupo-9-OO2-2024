package com.unla.grupo3.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Proveedor;
import com.unla.grupo3.repositories.IProveedorRepository;
import com.unla.grupo3.services.IProveedorService;

@Service("proveedorService")
public class ProveedorService implements IProveedorService {
	
	private IProveedorRepository proveedorRepository;
	
	public ProveedorService(IProveedorRepository proveedorRepository) {
		this.proveedorRepository = proveedorRepository;
	}
	
	// toma un proveedor y lo agrega 
	public Proveedor agregarModificarProveedor(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
		
	}
	
	//Busca y elimina a un proveedor por id
	public boolean eliminarProveedor(int id) {
		Optional<Proveedor> p=proveedorRepository.findById(id);
		if(p.isPresent()) {
			proveedorRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	//trae un proveedor por id
	public Optional<Proveedor> traerProveedor(int id) {
		return proveedorRepository.findById(id);
	}
	
	//Trae por nombre empresa
	public Optional<Proveedor> traerProveedor(String nombreEmpresa){
		return proveedorRepository.findByNombreEmpresa(nombreEmpresa);
	}
	
	//Trae por nombre empresa y cuil
	public Optional<Proveedor> traerProveedor(String nombreEmpresa, String cuil){
		return proveedorRepository.findByNombreEmpresaAndCuil(nombreEmpresa, cuil);
	}
	
	//trae todos los proveedores
	public List<Proveedor> traerProveedores(){
		return proveedorRepository.findAll();
	}

	
	

}
