package com.example.bikerental.dao.impl;

import com.example.bikerental.dao.RentalCostDAO;
import com.example.bikerental.entity.RentalCost;
import com.example.bikerental.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RentalCostDAOImpl implements RentalCostDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(RentalCostDAOImpl.class);

    private static final String SQL_ADD_RENTAL_COST = "INSERT INTO rental_cost " +
            "(bike_type_id,price) VALUES (?,?)";
    private static final String SQL_GET_RENTAL_COST_BY_ID = "SELECT rental_cost.id, bike_types.id, bike_types.type, " +
            "rental_cost.price FROM rental_cost LEFT JOIN bike_types ON rental_cost.bike_type_id = bike_types.id " +
            "WHERE rental_cost.id=?";
    private static final String SQL_GET_ALL_RENTAL_COSTS = "SELECT * FROM rental_cost";
    private static final String SQL_UPDATE_RENTAL_COST = "UPDATE rental_cost SET bike_type_id=?,price=? WHERE id=?";
    private static final String SQL_DELETE_RENTAL_COST = "DELETE * FROM rental_cost WHERE id=?";
    private static final String SQL_GET_PRICE_BY_BIKETEPE_ID = "SELECT price FROM rental_cost WHERE bike_type_id = ?";

    @Autowired
    public RentalCostDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int add(RentalCost entity) throws DAOException {
        return 0;
    }

    @Override
    public RentalCost getById(long id) throws DAOException {
        return null;
    }

    @Override
    public List<RentalCost> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(RentalCost entity) throws DAOException {

    }

    @Override
    public void delete(RentalCost entity) throws DAOException {

    }

    @Override
        public double getPriceByBikeTypeId(long bikeTypeId) throws DAOException {
            try {
                return jdbcTemplate.queryForObject(SQL_GET_PRICE_BY_BIKETEPE_ID, new Object[]{bikeTypeId}, Double.class);
            } catch (DataAccessException e) {
                LOGGER.error("An exception occurred in the layer DAO while get price by bikeType id from the DB", e);
                throw new DAOException("An exception occurred in the layer DAO while get price by bikeType id from the DB", e);
            }
    }
}
