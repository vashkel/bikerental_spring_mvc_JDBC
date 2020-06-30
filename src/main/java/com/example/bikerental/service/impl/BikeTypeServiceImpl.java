package com.example.bikerental.service.impl;

import com.example.bikerental.dao.BikeTypeDAO;
import com.example.bikerental.dao.impl.BikeTypeDAOImpl;
import com.example.bikerental.entity.BikeType;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.BikeTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeTypeServiceImpl implements BikeTypeService {

    private BikeTypeDAO bikeTypeDAO;
    private static final Logger LOGGER = LogManager.getLogger(BikeTypeDAOImpl.class);

    @Autowired
    public BikeTypeServiceImpl(BikeTypeDAO bikeTypeDAO) {
        this.bikeTypeDAO = bikeTypeDAO;
    }


    @Override
    public List<BikeType> getBikeTypes() throws ServiceException {
        List<BikeType> bikeTypes;
        try {
            bikeTypes = bikeTypeDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Get BikeTypes error:" + e);
            throw new ServiceException("Get BikeTypes error:" + e.getMessage());
        }
        return bikeTypes;
    }

    @Override
    public BikeType getById(long id) throws ServiceException {
        BikeType bikeType;
        try {
           bikeType = bikeTypeDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("get Bike by id error: " + e);
            throw new ServiceException("get Bike by id error: " + e.getMessage());
        }
        return bikeType;
    }
}