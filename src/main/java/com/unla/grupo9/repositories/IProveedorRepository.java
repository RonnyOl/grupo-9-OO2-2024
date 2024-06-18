package com.unla.grupo9.repositories;
import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo9.entities.Proveedor;


//Interfaz de ProveedorRepository
@Repository("proveedorRepository")
public interface IProveedorRepository extends JpaRepository<Proveedor, Serializable>{
	
	
	//Trae un Proveedor por su nombreEmpresa
	public abstract Optional<Proveedor> findByNombreEmpresa(String nombreEmpresa);
	
	//Trae un Proveedor por su nombreEmpresa y Cuil
	public abstract Optional<Proveedor> findByNombreEmpresaAndCuil(String nombreEmpresa, String cuil);
}
