package com.spring.service.impl;

import java.util.List;

import com.spring.repo.IGenericRepo;
import com.spring.service.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    // un único punto de entrada al repo generico con todas las operaciones
    protected abstract IGenericRepo<T, ID> getRepo();    

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) {
         // pendiente el tema de excepciones y otros
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        // Supplier -> Optional(T)
        // pendiente el tema de excepciones
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public void delete(ID id) {
        // pendiente el tema de excepciones
        getRepo().deleteById(id);
    }

}
