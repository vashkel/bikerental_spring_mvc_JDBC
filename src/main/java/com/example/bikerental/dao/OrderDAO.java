package com.example.bikerental.dao;

import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {

    boolean closeOrder(Order order) throws DAOException;

   List<Order> getAllOrdersByUserId(long userId) throws DAOException;

    List<Order> getAllOrders() throws DAOException;

    Order createOrder(Order order) throws DAOException;

    long getOrderIdByUserAndStatus(long id, String name) throws DAOException;

    Order findOpenOrder(User user) throws DAOException;

}
