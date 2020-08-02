package com.example.bikerental.service;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.ServiceException;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrderByUser(User user) throws ServiceException;

    List<Order> getAllOrders() throws ServiceException;

    Order createOrder(List<Bike> bikes, User user) throws ServiceException;

    Order findOpenOrder(User user) throws ServiceException;

    boolean closeOrder(Order order) throws ServiceException;

}
