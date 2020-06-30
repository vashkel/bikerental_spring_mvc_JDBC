package com.example.bikerental.dao;

import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;

public interface UserDAO extends AbstractDAO<User> {
    /**
     * Method finds user by his login
     *
     * @param login login of the user
     * @return {@code User} found user or {@code null}
     * @throws DAOException com.example.bikerental_spring_mvc.exception thrown in case error occurs
     */
    User findByLogin(String login) throws DAOException;


    /**
     * Method finds user by his id
     *
     * @param id login of the user
     * @return {@code User} found user or {@code null}
     * @throws DAOException com.example.bikerental_spring_mvc.exception thrown in case error occurs
     */
    User findByID(String id) throws DAOException;
    /**
     * Method find id of user by his login
     *
     * @param login login of certain user
     * @return {@code long} id of found user or {@code null}
     * @throws DAOException com.example.bikerental_spring_mvc.exception thrown in case error occurs
     */
    long findIdByLogin(String login) throws DAOException;

    /**
     * Adds User to DB.
     *
     * @param user - User object that should be added in database.
     * @throws DAOException if occurred SQL com.example.bikerental_spring_mvc.exception.
     */
    void register(User user) throws DAOException;

    /**
     * Delete User from DB.
     *
     * @param id - id of User that should delete.
     * @throws DAOException if occurred SQL com.example.bikerental_spring_mvc.exception.
     */
    void deleteUserById(long id) throws DAOException;

    /**
     * Change user status  to DB.
     *
     * @param userId - id of User that should delete.
     * @throws DAOException if occurred SQL com.example.bikerental_spring_mvc.exception.
     */
    int changeStateById(long userId, String state) throws DAOException ;

    /**
     * Change user password  to DB.
     *
     * @param password - that will change.
     * @param user - user which changes password.
     * @throws DAOException if occurred SQL com.example.bikerental_spring_mvc.exception.
     */
    void updatePassword(String password, User user) throws DAOException;
}

