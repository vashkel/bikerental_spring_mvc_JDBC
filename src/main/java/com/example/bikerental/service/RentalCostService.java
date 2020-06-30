package com.example.bikerental.service;

import com.example.bikerental.exception.ServiceException;

public interface RentalCostService {


    double getPriceByBikeTypeId(long bikeTypeId) throws ServiceException;
}
