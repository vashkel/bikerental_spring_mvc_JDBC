package com.example.bikerental.service.impl;

import com.example.bikerental.dao.OrderDAO;
import com.example.bikerental.dao.RentalCostDAO;
import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.OrderStatusEnum;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.OrderService;
import com.example.bikerental.util.RequestTimeParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private OrderDAO orderDAO;
    private RentalCostDAO rentalCostDAO;


    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, PlatformTransactionManager transactionManager, RentalCostDAO rentalCostDAO) {
        this.orderDAO = orderDAO;
        this.rentalCostDAO = rentalCostDAO;
    }

    @Override
    public List<Order> getAllOrderByUser(User user) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.getAllOrdersByUserId(user.getId());
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown when searching for all users", e);
            throw new ServiceException("An exception was thrown when searching for all orders by userId", e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.getAllOrders();
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown while getting all orders : ", e);
            throw new ServiceException("An exception was thrown while getting all orders : ", e.getMessage());
        }
        return orders;
    }



    @Override
    public Order findOpenOrder(User user) throws ServiceException {
        Order order;
        try {
            order = orderDAO.findOpenOrder(user);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during find open order : ", e);
            throw new ServiceException("An exception was thrown during find open order : ", e);
        }
        return order;    }

        @Override
        @Transactional
    public Order createOrder(List<Bike> bikes, User user) throws ServiceException {
        Order order = new Order();
        order.setStart_date(LocalDateTime.now());
        order.setUser(user);
        order.setBikes(bikes);
        order.setStatus(OrderStatusEnum.ACTIVE);
        try {
            orderDAO.createOrder(order);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during create order : ", e);
            throw new ServiceException("An exception was thrown during create order : ", e);
        }
        return order;
    }

    @Override
    @Transactional
    public boolean closeOrder(Order order) throws ServiceException {
        boolean isPerformed;
        double hoursNumber = RequestTimeParameter.getParam(order.getStart_date());
        double commonPriceHour = 0;

        try {
            order.setEnd_date(LocalDateTime.now());
            order.setStatus(OrderStatusEnum.FINISHED);
            for(Bike bike : order.getBikes()){
                commonPriceHour += rentalCostDAO.getPriceByBikeTypeId(bike.getBikeType().getId());
            }
            order.setSum(commonPriceHour * hoursNumber);
            isPerformed = orderDAO.closeOrder(order);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during find open order : ", e);
            throw new ServiceException("An exception was thrown during find open order : ", e);
        }
        return isPerformed;
    }


}
