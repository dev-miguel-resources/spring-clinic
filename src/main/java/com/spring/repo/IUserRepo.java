package com.spring.repo;

import com.spring.model.User;

public interface IUserRepo extends IGenericRepo<User, Integer> {
    
    // pendiente de querys
    // 1. obtener referencias de usuarios por su username (derived queries or query methods)
    User findOneByUsername(String username);
}
