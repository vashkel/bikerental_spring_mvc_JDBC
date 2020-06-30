package com.example.bikerental.dao;

import com.example.bikerental.entity.RentalCost;
import com.example.bikerental.exception.DAOException;

public interface RentalCostDAO extends AbstractDAO<RentalCost>{


    double getPriceByBikeTypeId(long bikeTypeId) throws DAOException;
}
