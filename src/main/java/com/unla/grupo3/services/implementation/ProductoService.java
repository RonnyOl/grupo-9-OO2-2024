package com.unla.grupo3.services.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unla.grupo3.entities.UserRole;
import com.unla.grupo3.repositories.IProductoRepository;
import com.unla.grupo3.repositories.IUserRepository;

@Service("productoService")
public class ProductoService  {

	private IProductoRepository productoRepository;

	public ProductoService(IProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	//IMPLEMENTAR ABM DE PRODUCTO ADEMAS DE ALGUNOS TRAER BASICOS PARA LA LISTA DE PRODUCTOS Y EL PRODUCTO PARTICULAR
	
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
	
	
	CONSULTAS A LA BD PARA PRODUCTO:
	//service de producto
	-agregarProducto
	-modificarProducto
	-eliminarProducto
	-traerProducto(int id)
	-traerProducto(String nombre)
	-traerProductos //devuelve la lista completa
	-deshabilitarProducto
	-eliminarProducto
	*/
	
}