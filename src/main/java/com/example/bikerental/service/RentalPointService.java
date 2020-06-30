package com.example.bikerental.service;

import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.exception.ServiceException;

import java.util.List;

public interface RentalPointService {

    List<RentalPoint> getRentalPoints() throws ServiceException;

    RentalPoint getById(long id) throws ServiceException;
}
