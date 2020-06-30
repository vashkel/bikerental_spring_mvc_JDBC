package com.example.bikerental.dao.impl;

import com.example.bikerental.dao.RentalPointDAO;
import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.mapper.RentalPointMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalPointDAOImpl implements RentalPointDAO {
    private static final Logger LOGGER = LogManager.getLogger(RentalPointDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RentalPointDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String SQL_ADD_RENTAL_POINT = "INSERT INTO `bike-rental`.rental_points(name, adress, tel)" +
            " VALUES (?,?,?)";
    private static final String SQL_GET_RENTAL_POINT_BY_ID = "SELECT * FROM rental_points WHERE id=?";

    private static final String SQL_GET_ALL_RENTAL_POINTS = "SELECT * FROM `bike-rental`.rental_points";

    private static final String SQL_UPDATE_RENTAL_POINT = "UPDATE `bike-rental`.rental_points SET name=?,adress=?,tel=? " +
            "WHERE id=?";

    private static final String SQL_DELETE_RENTAL_POINT = "DELETE * FROM rental_points WHERE id=?";

    @Override
    public int add(RentalPoint entity) throws DAOException {
        return 0;
    }

    @Override
    public RentalPoint getById(long id) throws DAOException {
        return jdbcTemplate.queryForObject(SQL_GET_RENTAL_POINT_BY_ID, new RentalPointMapper(),id);
    }

    @Override
    public List<RentalPoint> getAll() throws DAOException {
        return jdbcTemplate.query(SQL_GET_ALL_RENTAL_POINTS, new RentalPointMapper());
    }

    @Override
    public void update(RentalPoint entity) throws DAOException {

    }

    @Override
    public void delete(RentalPoint entity) throws DAOException {

    }
}
