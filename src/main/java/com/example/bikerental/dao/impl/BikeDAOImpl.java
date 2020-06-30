package com.example.bikerental.dao.impl;

import com.example.bikerental.dao.BikeDAO;
import com.example.bikerental.entity.Bike;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.mapper.BikeMapper;
import com.example.bikerental.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BikeDAOImpl implements BikeDAO {
    private final static Logger LOGGER = LogManager.getLogger(BikeDAOImpl.class);

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public BikeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_ADD_BIKE =
            "INSERT INTO bikes (brand, model, bike_type_id, rental_point_id, status) VALUES (?,?,?,?,?)";

    private static final String SQL_GET_ALL_BIKES_BY_LIMIT = "SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id WHERE bikes.id > ?" +
            " ORDER BY bikes.id LIMIT ? ";

    private static final String SQL_GET_ALL_BIKES = "SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id";

    private static final String SQL_GET_BIKE_BY_ID = "SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes " +
            "LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points ON bikes.rental_point_id = rental_points.id WHERE bikes.id = ?";

    private static final String SQL_UPDATE_BIKE = "UPDATE bikes SET brand=?,model=?, bike_type_id=?," +
            "rental_point_id=?,status=? WHERE id=?";

    private static final String SQL_DELETE_BIKE = "DELETE FROM bikes WHERE id=?";
    private static final String SQL_GET_ALL_BRANDS = "SELECT DISTINCT brand from bikes";
    private static final String SQL_GET_BIKE_BY_TYPE_AND_RENTAL_POINT_ID = "SELECT " +
            "bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id," +
            " rental_points.name, rental_points.adress, rental_points.tel ,bikes.status " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "LEFT JOIN rental_points  ON bikes.rental_point_id = rental_points.id " +
            "WHERE bikes.bike_type_id = ? AND bikes.rental_point_id = ? AND bikes.status = ?";

    private static final String SQL_GET_FREE_BIKES_BY_RENTAL_POINT_ID = "SELECT bike_types.type, COUNT(bikes.id) as count_bikes " +
            "FROM bikes LEFT JOIN bike_types ON bikes.bike_type_id = bike_types.id " +
            "WHERE bikes.rental_point_id = ? AND status = 'free' " +
            "GROUP BY bike_type_id";

    private static final String SQL_GET_BIKES_BY_OPEN_ORDER_ID = "SELECT bikes.id, bikes.brand, bikes.model, bikes.bike_type_id, bike_types.type, bikes.rental_point_id, rental_points.name, rental_points.adress, rental_points.tel, bikes.status FROM order_bikes INNER JOIN bikes ON order_bikes.bike_id = bikes.id INNER JOIN bike_types ON bikes.bike_type_id = bike_types.id INNER JOIN rental_points ON bikes.rental_point_id = rental_points.id WHERE order_id = ?";


    @Override
    public List<Bike> getAllBikeByLimit(PageInfo pageInfo) throws DAOException {
        return null;
    }

    @Override
    public List<String> getAllBrandBike() throws DAOException {
        return null;
    }

    @Override
    public Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws DAOException {
        Bike bike;
        try {
            List<Bike> freeBikes;
            freeBikes = jdbcTemplate.query(SQL_GET_BIKE_BY_TYPE_AND_RENTAL_POINT_ID, new Object[]{bikeTypeId, rentalPointId, "free"}, new BikeMapper());
            bike = freeBikes.get(0);
            return bike;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting bike from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting bike from the DB", e);
        }
    }

    @Override
    public void changeBikeStatusOnBusy(Bike bike) throws DAOException {

    }

    @Override
    public void addSomeBikes(Bike bike, int countBike) throws DAOException {
       try{
           for (int x=0;x<countBike;x++) {
               jdbcTemplate.update(SQL_ADD_BIKE,bike.getBrand(),bike.getModel(),bike.getBikeType().getId(),bike.getRentalPoint().getId(),bike.getBikeStatus().name());
       }
       }catch (DataAccessException e){
           LOGGER.error("An exception while add bikes", e);
           throw new DAOException("An exception while add bikes", e);
       }
    }

    @Override
    public int changeStatusById(long bikeId, String status) throws DAOException {
        return 0;
    }

    @Override
    public Map<String, String> getAvailableBikesByRentalPointId(long rentalPointId) throws DAOException {
        return null;
    }

    @Override
    public List<Bike> getBikesByOpenOrder(long orderId) throws DAOException {
        List<Bike> bikesOpenOrder;
        try {
            bikesOpenOrder = jdbcTemplate.query(SQL_GET_BIKES_BY_OPEN_ORDER_ID, new BikeMapper(), orderId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Exception while getting bikes of open order", e);
            throw new DAOException("Exception while getting bikes of open order", e);
        }
        return bikesOpenOrder;
    }

    @Override
    public int add(Bike entity) throws DAOException {
        return 0;
    }

    @Override
    public Bike getById(long id) throws DAOException {
        return null;
    }

    @Override
    public List<Bike> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(Bike entity) throws DAOException {

    }

    @Override
    public void delete(Bike entity) throws DAOException {

    }
}
