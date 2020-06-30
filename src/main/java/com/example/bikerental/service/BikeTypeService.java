package com.example.bikerental.service;

import com.example.bikerental.entity.BikeType;
import com.example.bikerental.exception.ServiceException;

import java.util.List;

public interface BikeTypeService {

    List<BikeType> getBikeTypes() throws ServiceException;

    BikeType getById(long id) throws ServiceException ;
}
