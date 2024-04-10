package com.spring.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.spring.exception.ModelNotFoundException;
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
    public T update(T t, ID id) throws Exception {
         // API JAVA: REFLEXION
         Class<?> classType = t.getClass();
         String className = t.getClass().getSimpleName();
         String methodName = "setId" + className;
         Method setIdMethod = classType.getMethod(methodName, id.getClass());
         setIdMethod.invoke(t, id);

        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND" + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND" + id));
    }

    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND" + id));
        getRepo().deleteById(id);
    }

}
