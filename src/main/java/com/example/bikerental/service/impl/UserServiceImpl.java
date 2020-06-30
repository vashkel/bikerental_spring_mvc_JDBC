package com.example.bikerental.service.impl;

import com.example.bikerental.dao.UserDAO;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ExceptionMessage;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.generator.HashGenerator;
import com.example.bikerental.service.UserService;
import com.example.bikerental.validator.Validator;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
    public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;
    private HashGenerator HASH_GENERATOR;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, HashGenerator hash_generator) {
        this.userDAO = userDAO;
        this.HASH_GENERATOR = hash_generator;
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        if (!Validator.validateLogin(login)) {
            LOGGER.debug("login validation error " + login);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        if (!Validator.validatePassword(password)) {
            LOGGER.debug("password validation error");
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        try {
            user = userDAO.findByLogin(login);
            if (user != null) {
                String hash = HASH_GENERATOR.generateHash(password, user.getSalt());
                if (hash.equals(user.getPassword())) {
                    return user;
                }
            }
        } catch (DAOException e) {
            LOGGER.error(" error while login user " + e);
            throw new ServiceException(" error while login user " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("get all users error", e);
            throw new ServiceException("get all users error", e.getMessage());
        }
        return users;
    }
    @Override
    public void deleteUserById(long id) throws ServiceException {
        try {
            userDAO.deleteUserById(id);
        } catch (DAOException e) {
            LOGGER.error("delete user error : " + e);
            throw new ServiceException("delete user error : " + e.getMessage());
        }
    }

    @Override
    public void changeStateById(long userId, String state) throws ServiceException {

    }

    @Override
    public User getByID(long id) throws ServiceException {
        User user;
        try {
            user = userDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("Exception was thrown while getting user by id: " + e);
            throw new ServiceException("Exception was thrown while getting user by id: " + e.getMessage());
        }
        return user;
    }

    @Override
    public void changePassword(String currentPassword, String password, User user) throws ServiceException {

    }

    @Override
    public boolean register(User newUser) throws ServiceException {
        Pair<String, String> hashSalt;
        if (!Validator.validateLogin(newUser.getLogin())) {
            LOGGER.error("findByLogin validation error= " + newUser.getLogin());
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        long id;
        try {
            id = userDAO.findIdByLogin(newUser.getLogin());
        } catch (DAOException e) {
            throw new ServiceException("get user id by login error ", e);
        }
        if (id > 0) {
            return false;
        }
        if (!Validator.validatePassword(newUser.getPassword())) {
            LOGGER.error("password validation error, password = " + newUser.getPassword());
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        try {
            hashSalt = new HashGenerator().generateHashSalt(newUser.getPassword());
            newUser.setPassword(hashSalt.getKey());
            newUser.setSalt(hashSalt.getValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!Validator.validateName(newUser.getName())) {
            LOGGER.error("name validation error, name = " + newUser.getName());
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        if (!Validator.validateName(newUser.getSurname())) {
            LOGGER.error("surname validation error, surname = " + newUser.getSurname());
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        if (!Validator.validateEmail(newUser.getEmail())) {
            LOGGER.error("email validation error, email = " + newUser.getEmail());
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        try {
             userDAO.register(newUser);
        } catch (DAOException e) {
            LOGGER.error("Exception to DB while add user", e);
            throw new ServiceException("Exception to DB while add user", e);
        }
        return true;
    }
}
