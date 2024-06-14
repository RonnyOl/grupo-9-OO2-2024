package com.unla.grupo3.repositories;
import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Proveedor;

@Repository("proveedorRepository")
public interface IProveedorRepository extends JpaRepository<Proveedor, Serializable>{
	
	public abstract Optional<Proveedor> findByNombreEmpresa(String nombreEmpresa);
	
	public abstract Optional<Proveedor> findByNombreEmpresaAndCuil(String nombreEmpresa, String cuil);
}
