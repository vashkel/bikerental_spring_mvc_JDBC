package com.example.bikerental.dao.impl;

import com.example.bikerental.dao.BikeTypeDAO;
import com.example.bikerental.entity.BikeType;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.mapper.BikeTypeMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BikeTypeDAOImpl implements BikeTypeDAO {

    private JdbcTemplate jdbcTemplate;
    private final static Logger LOGGER = LogManager.getLogger(BikeTypeDAOImpl.class);

    @Autowired
    public BikeTypeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String SQL_ADD_BIKE_TYPE = "INSERT INTO  bike_types(type) value (?)";
    private final static String SQL_GET_BY_ID = "SELECT id,type FROM bike_types WHERE id = ?";
    private final static String SQL_GET_ALL = "SELECT id,type FROM bike_types";
    private final static String SQL_UPDATE_BIKE_TYPE = "UPDATE `bike-rental`.bike_types SET id=?,type=? WHERE id=?";
    private final static String SQL_DELETE_BIKE_TYPE = "DELETE * FROM `bike-rental`.bike_types WHERE id=?";


    @Override
    public int add(BikeType entity) throws DAOException {
        return 0;
    }

    @Override
    public BikeType getById(long id) throws DAOException {
       return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new BikeTypeMapper(),id);
    }

    @Override
    public List<BikeType> getAll() throws DAOException {
        return jdbcTemplate.query(SQL_GET_ALL, new BikeTypeMapper());
    }

    @Override
    public void update(BikeType entity) throws DAOException {

    }

    @Override
    public void delete(BikeType entity) throws DAOException {

    }
}