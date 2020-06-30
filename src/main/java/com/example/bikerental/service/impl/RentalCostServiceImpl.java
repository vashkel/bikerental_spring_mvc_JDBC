package com.example.bikerental.service.impl;

import com.example.bikerental.dao.RentalCostDAO;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.RentalCostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalCostServiceImpl implements RentalCostService {
    private static final Logger LOGGER = LogManager.getLogger(RentalCostServiceImpl.class);
    private RentalCostDAO rentalCostDao;

    @Autowired
    public RentalCostServiceImpl(RentalCostDAO rentalCostDao) {
        this.rentalCostDao = rentalCostDao;
    }

    @Override
    public double getPriceByBikeTypeId(long bikeTypeId) throws ServiceException {
        double price;
        try {
            price = rentalCostDao.getPriceByBikeTypeId(bikeTypeId);
        } catch (DAOException e) {
            LOGGER.error("An exception was thrown during getting price by bike type id : ", e);
            throw new ServiceException("An exception was thrown during getting price by bike type id : ", e.getMessage());
        }
        return price;
    }
}
