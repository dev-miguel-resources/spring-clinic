package com.spring.service;

import java.util.List;

public interface ICRUD<T, ID> {

    // pendiente para más adelante

    T save(T t);

    T update(T t, ID id);

    List<T> findAll();

    T findById(ID id);

    void delete(ID id);

}
