package com.unla.grupo3.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Proveedor;
import com.unla.grupo3.repositories.IProveedorRepository;
import com.unla.grupo3.services.IProveedorService;


//Implementacion de la Interfaz ProveedorService
@Service("proveedorService")
public class ProveedorService implements IProveedorService {
	
	//Vincula el Repositorio
	private IProveedorRepository proveedorRepository;
	
	//
	public ProveedorService(IProveedorRepository proveedorRepository) {
		this.proveedorRepository = proveedorRepository;
	}
	
	//Agrega o Modifica un Proveedor
	public Proveedor agregarModificarProveedor(Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
		
	}
	
	//Elimina un Proveedor
	public boolean eliminarProveedor(int id) {
		Optional<Proveedor> p=proveedorRepository.findById(id);
		if(p.isPresent()) {
			proveedorRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	//Trae un Proveedor por id
	public Optional<Proveedor> traerProveedor(int id) {
		return proveedorRepository.findById(id);
	}
	
	//Trae un Proveedor por nombreEmpresa
	public Optional<Proveedor> traerProveedor(String nombreEmpresa){
		return proveedorRepository.findByNombreEmpresa(nombreEmpresa);
	}
	
	//Trae un Proveedor por nombreEmpresa y Cuil
	public Optional<Proveedor> traerProveedor(String nombreEmpresa, String cuil){
		return proveedorRepository.findByNombreEmpresaAndCuil(nombreEmpresa, cuil);
	}
	
	//Trae Lista de Proveedores
	public List<Proveedor> traerProveedores(){
		return proveedorRepository.findAll();
	}

	
	

}
