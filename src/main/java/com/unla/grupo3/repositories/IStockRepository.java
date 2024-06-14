package com.unla.grupo3.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupo3.entities.Stock;

@Repository("stockRepository")
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

    //@Query("SELECT u FROM User u JOIN FETCH u.userRoles WHERE u.username = (:username)")
    //public abstract User findByUsernameAndFetchUserRolesEagerly(@Param("username") String username);

}