package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

// pendiente para más adelante
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID>  {
    
}
