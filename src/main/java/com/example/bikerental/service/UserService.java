package com.example.bikerental.service;

import com.example.bikerental.entity.User;
import com.example.bikerental.exception.ServiceException;

import java.util.List;

public interface UserService {

    User login(String login, String password) throws ServiceException;


    List<User> getAllUsers() throws ServiceException;

    void deleteUserById(String id) throws ServiceException;

    void changeStateById(String userId, String state) throws ServiceException;

    User getByID(long id) throws ServiceException;

    void changePassword(String currentPassword, String password, User user) throws ServiceException;

    boolean register(User newUser) throws ServiceException;
}