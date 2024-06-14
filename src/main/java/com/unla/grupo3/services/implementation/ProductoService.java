package com.unla.grupo3.services.implementation;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.repositories.IProductoRepository;

@Service("productoService")
public class ProductoService  {

	private IProductoRepository productoRepository;

	public ProductoService(IProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	//IMPLEMENTAR ABM DE PRODUCTO ADEMAS DE ALGUNOS TRAER BASICOS PARA LA LISTA DE PRODUCTOS Y EL PRODUCTO PARTICULAR
	
	//AGREGAR O MODIFICAR PRODUCTO
	
	public boolean agregarOModificarProducto(Producto producto) {
		productoRepository.save(producto);
		return true;
	}
	
	//ELIMINAR PRODUCTO
	
	public boolean eliminarProducto(int id) {
		Optional<Producto> p=productoRepository.findById(id);
		if(p.isPresent()) {
			productoRepository.delete(p.get());
			return true;
		}
		return false;
	}
	
	//HABILITAR O DESHABILITAR PRODUCTO
	
	public boolean cambiarEstadoDeProducto(int id,boolean nuevoEstado) {
		Optional<Producto> p=productoRepository.findById(id);
		if(p.isPresent()) {
			p.get().setHabilitado(nuevoEstado);
			this.agregarOModificarProducto(p.get());
			return true;
		}
		return false;
	}
	
	//TRAER PRODUCTO POR ID CON SU STOCK
	
	public Optional<Producto> traerProducto(int id) {
		return productoRepository.findById(id);
	}
	
	//TRAER PRODUCTO POR NOMBRE CON SU STOCK
	
	public Optional<Producto> traerProducto(String nombre) {
		return productoRepository.findByNombre(nombre);	
	}
	
	//TRAER LISTA DE PRODUCTOS CON SU STOCK 
	public List<Producto> traerProductos(){
		return productoRepository.findAll();
	}
	
	
	
	
	
	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.unla.grupo3.entities.User user = userRepository.findByUsernameAndFetchUserRolesEagerly(username);
		return buildUser(user, buildGrantedAuthorities(user.getUserRoles()));
	}

	private User buildUser(com.unla.grupo3.entities.User user, List<GrantedAuthority> grantedAuthorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
						true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
						grantedAuthorities);
	}

	private List<GrantedAuthority> buildGrantedAuthorities(Set<UserRole> userRoles) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(UserRole userRole: userRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<>(grantedAuthorities);
	}
	*/
	
}