package com.example.bikerental.service.impl;

import com.example.bikerental.dao.BikeDAO;
import com.example.bikerental.entity.Bike;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.BikeService;
import com.example.bikerental.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BikeServiceImpl implements BikeService {

    private static final Logger LOGGER = LogManager.getLogger(BikeServiceImpl.class);

    private BikeDAO bikeDAO;

    @Autowired
    public BikeServiceImpl(BikeDAO bikeDAO) {
        this.bikeDAO = bikeDAO;
    }

    @Override
    public List<Bike> getAllBike(PageInfo pageInfo) throws ServiceException {
        return null;
    }

    @Override
    public List<String> getAllBikeBrand() throws ServiceException {
        List<String> brands;
        try {
            brands = bikeDAO.getAllBrandBike();
        } catch (DAOException e) {
            LOGGER.error("get all brands of Bike error " + e);
            throw new ServiceException("get all brands of Bike error " + e.getMessage());
        }

        return brands;
    }


    @Override
    public Bike getBikeById(long id) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("getBikeById error " + e);
            throw new ServiceException("getBikeById error " + e.getMessage());
        }
        return bike;
    }


    @Override
    public void addBike(Bike bike) throws ServiceException {
        try {
            bikeDAO.add(bike);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while adding bike: " + e);
            throw new ServiceException("Exception occurred while adding bike: " + e.getMessage());
        }
    }

    @Override
    public void addSomeBikes(Bike bike, int countBike) throws ServiceException {
        try {
            bikeDAO.addSomeBikes(bike, countBike);
        } catch (DAOException e) {
            LOGGER.error("Add some bikes Exception: " + e);
            throw new ServiceException("Add some bikes Exception: " + e.getMessage());
        }
    }

    @Override
    public Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getBikeByTypeAndRentalPointId(bikeTypeId, rentalPointId);
            if (bike != null) {
                bikeDAO.changeBikeStatusOnBusy(bike);
            }
        } catch (DAOException e) {
            LOGGER.error("getBikeByTypeAndRentalPointId " + e);
            throw new ServiceException("getBikeById error " + e.getMessage());
        }
        return bike;
    }

    @Override
    public void changeStatusById(long bikeId, String status) throws ServiceException {
        try {
            bikeDAO.changeStatusById(bikeId, status);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while change bike status by id: " + e);
            throw new ServiceException("Exception occurred while change bike status by id: " + e.getMessage());
        }
    }

    @Override
    public void deleteBikeById(long bikeId) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getById(bikeId);
            bikeDAO.delete(bike);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while delete bike by id: " + e);
            throw new ServiceException("Exception occurred while delete bike by id: " + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getAvailableBikesByRentalPointId(long rentalPointId) throws ServiceException {
        Map<String,String> bikesByType = new HashMap<>();
        try {
            bikesByType = bikeDAO.getAvailableBikesByRentalPointId(rentalPointId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return bikesByType;
    }

    @Override
    public List<Bike> getBikesByOpenOrder(long orderId) throws ServiceException {
        List<Bike> bikesOpenOrder;
        try {
            bikesOpenOrder = bikeDAO.getBikesByOpenOrder(orderId);
        }catch (DAOException e ){
            LOGGER.error("Exception while getting BikesOpenOrder from DB");
            throw new ServiceException("Exception to DB while getting bikesOpenOrder");
        }
        return bikesOpenOrder;
    }
}
