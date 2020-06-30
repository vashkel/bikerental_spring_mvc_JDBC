package com.example.bikerental.service.impl;

import com.example.bikerental.dao.RentalPointDAO;
import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.RentalPointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalPointServiceImpl implements RentalPointService {

    private static final Logger LOGGER = LogManager.getLogger(RentalPointServiceImpl.class);
    private RentalPointDAO rentalPointDAO;
     @Autowired
    public RentalPointServiceImpl(RentalPointDAO rentalPointDAO) {
        this.rentalPointDAO = rentalPointDAO;
    }


    @Override
    public List<RentalPoint> getRentalPoints() throws ServiceException {
        List<RentalPoint> rentalPoints;
        try {
            rentalPoints = rentalPointDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while retrieving rental points", e);
            throw new ServiceException("Exception occurred while retrieving rental points: " + e.getMessage());
        }
        return rentalPoints;
    }

    @Override
    public RentalPoint getById(long id) throws ServiceException {
        RentalPoint rentalPoint;
        try {
            rentalPoint = rentalPointDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while retrieving rental point by id ", e);
            throw new ServiceException("Exception occurred while retrieving rental point by id : " + e.getMessage());
        }
        return rentalPoint;
    }
    }
