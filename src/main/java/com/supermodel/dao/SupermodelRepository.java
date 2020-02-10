package com.supermodel.dao;

import com.supermodel.model.Supermodel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("mysqlSupermodels")
public interface SupermodelRepository extends CrudRepository<Supermodel, Integer> {
    Supermodel findByName(String name);
}
