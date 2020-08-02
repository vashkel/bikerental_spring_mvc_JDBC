package com.example.bikerental.dao;


import com.example.bikerental.entity.Entity;
import com.example.bikerental.exception.DAOException;

import java.util.List;

/**
 * The base interface of DAO layer.
 * All entity-specified interfaces of DAO layer extends this interface.
 * Includes realization of such common methods as closing connection, statement,
 * connection and statement together
 * @param <T> concrete entity for which DAO interface is created
 *
 */
public interface AbstractDAO<T extends Entity> {

    /**
     * Method adds given entity to the concrete entity table
     *
     * @param entity specified concrete entity
     * @throws DAOException is thrown if error occurs while adding entity
     */
    int add(T entity) throws DAOException;


    /**
     * Method gets and retrieves concrete entity by its id
     *
     * @param id id of entity
     * @return concrete entity
     * @throws DAOException is thrown if error occurs while retrieving entity
     */
    T getById(long id) throws DAOException;


    /**
     * Returns all entities
     *
     * @return List of accounts
     * @throws DAOException is thrown if error occurs while retrieving all entities
     */
    List<T> getAll() throws DAOException;


    /**
     * Updates existing account
     *
     * @param entity
     * @return void
     */
    void update(T entity) throws DAOException;


    /**
     * Method deletes entity
     *
     * @param id
     * @throws DAOException com.example.bikerental_spring_mvc.exception thrown in case error occurs
     */
    void delete(Long id) throws DAOException;

}

