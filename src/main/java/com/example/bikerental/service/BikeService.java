package com.example.bikerental.service;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.util.PageInfo;

import java.util.List;
import java.util.Map;

public interface BikeService {
    List<Bike> getAllBike(PageInfo pageInfo) throws ServiceException;

    List<Bike> getAll() throws ServiceException;

    List<String> getAllBikeBrand() throws ServiceException;

    Bike getBikeById(long id) throws ServiceException;

    void addBike(Bike bike) throws ServiceException;

    void addSomeBikes(Bike bike, int countBike) throws ServiceException;

    Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws ServiceException;

    void changeStatusById(String bikeId, String status) throws ServiceException;

    void deleteBikeById(String bikeId) throws ServiceException ;

    Map<String, String> getAvailableBikesByRentalPointId(long rentalPointId) throws ServiceException;

    List<Bike> getBikesByOpenOrder(long id) throws ServiceException;
}
