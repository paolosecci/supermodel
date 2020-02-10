package com.supermodel.dao;

import com.supermodel.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("mysqlUsers")
public interface UserRepository extends CrudRepository<User, Integer>{

    User findByUsername(String username);
}
