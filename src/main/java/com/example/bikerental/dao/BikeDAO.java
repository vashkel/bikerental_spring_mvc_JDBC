package com.example.bikerental.dao;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.util.PageInfo;

import java.util.List;
import java.util.Map;

public interface BikeDAO extends AbstractDAO<Bike> {

    /**
     * @param pageInfo of the PageInfo with parameters for the LIMIT operator
     * @return List of bikes
     * @throws DAOException
     */
    List<Bike> getAllBikeByLimit(PageInfo pageInfo) throws DAOException;


    /**
     * method gets all brands of bike
     * @return List of bikes brand
     * @throws DAOException
     */
    List<String> getAllBrandBike() throws DAOException;

    /**
     * method gets bike by type and rental pointId
     * @return appropriate bike
     * @throws DAOException
     */
    Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws DAOException;

    /**
     * method changes status of bike on busy for unavailable getting current bike with another user
     * @return void
     * @throws DAOException
     */
    void changeBikeStatusOnBusy(Bike bike) throws DAOException;

    void addSomeBikes(Bike bike, int countBike) throws DAOException;

    int changeStatusById(long bikeId, String status) throws DAOException;

    Map<String, String> getAvailableBikesByRentalPointId(long rentalPointId) throws DAOException;

    List<Bike> getBikesByOpenOrder(long orderId) throws DAOException;
}
