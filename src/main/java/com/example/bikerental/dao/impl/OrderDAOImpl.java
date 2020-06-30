package com.example.bikerental.dao.impl;


import com.example.bikerental.dao.OrderDAO;
import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.BikeStatusEnum;
import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.mapper.OrderMapper;
import com.example.bikerental.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderBikesInserter;


    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders").usingColumns("start_date", "end_date", "user_id", "status", "sum")
                .usingGeneratedKeyColumns("id");
        this.orderBikesInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("order_bikes");
    }

    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET " +
            "start_date=?,end_date=?,user_id=?,bike_id=?,status=?,sum=? WHERE id=?";
    private static final String SQL_ADD_ORDER = "INSERT INTO orders" +
            "(start_date, end_date, user_id, bike_id, status, sum) VALUES (?,?,?,?,?,?)";
    private static final String SQL_DELETE_ORDER = "DELETE * FROM orders WHERE id=?";
    private static final String SQL_GET_ORDER_BY_ID = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE o.id=?";

    private static final String SQL_GET_ORDER_BY_USER_ID = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE u.id=? AND o.end_date IS NOT NULL ";

    private static final String SQL_GET_ALL_ORDERS = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE o.end_date IS NOT NULL";

    private static final String SQL_GET_ALL_ORDERS_BY_LIMIT = "SELECT o.id, o.start_date, o.end_date,o.user_id, u.id," +
            " o.bike_id, bk.id, o.status, o.sum ,u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            " u.balance,bk.brand, bk.model, bk.bike_type_id, bt.id, bk.rental_point_id, rp.id, bk.status, " +
            "bt.type, rp.name,rp.adress, rp.tel " +
            "FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "LEFT JOIN bikes AS bk ON o.bike_id = bk.id " +
            "LEFT JOIN bike_types AS bt ON bk.bike_type_id = bt.id " +
            "LEFT JOIN rental_points AS rp ON bk.rental_point_id = rp.id WHERE o.end_date IS NOT NULL AND o.id > ?" +
            " ORDER BY o.id LIMIT ? ";

    private static final String SQL_FIND_OPEN_ORDER = "SELECT o.id, o.start_date, o.end_date,o.user_id, o.status, " +
            "o.sum , u.id, u.name, u.surname, u.login, u.password, u.role, u.tel, u.state," +
            "u.balance FROM orders AS o " +
            "LEFT JOIN users AS u ON o.user_id = u.id " +
            "WHERE u.id=? AND o.end_date IS NULL";

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders (start_date, user_id, bike_id, status, sum)" +
            " VALUES (?,?,?,?,?)";
    private static final String SQL_CLOSE_ORDER = "UPDATE orders SET end_date = ? , status = ?, sum = ? WHERE id = ?";
    private static final String SQL_CLOSE_ORDER_GIVE_AWEY_BIKE = "UPDATE bikes SET status = ? WHERE id = ?";
    private static final String SQL_SET_NEW_BALANCE = "UPDATE users SET balance = ? where id = ?";
    private static final String SQL_GET_ORDER_ID_BY_USER_ID_AND_STATUS = "SELECT id FROM orders WHERE user_id = ? AND status = ?";
    private static final String SQL_CHANGE_STATUS_BIKE = "UPDATE bikes SET status = ? WHERE id = ?";


    @Override
    public List<Order> getAllOrdersByUserId(long userId) throws DAOException {
        return null;
    }

    @Override
    public List<Order> getAllByLimit(PageInfo pageInfo) throws DAOException {
        return null;
    }

    @Override
    public Order createOrder(Order order) throws DAOException {
        if (order==null){
            return null;
        }
        try{
            long orderId = saveOrderDetails(order);
            order.setId(orderId);
            List<Bike> bikes = order.getBikes();
            for (Bike bike : bikes){
                saveBikeToOrder(bike, orderId);
                changeBikeStatus(bike.getId(), BikeStatusEnum.BUSY.toString());
            }
        }catch (DataAccessException e){
            LOGGER.error("Exception was thrown during creating order to the DB : ", e);
            throw new DAOException("Exception was thrown during creating order to the DB : ", e);
        }
        return order;
    }

    private void changeBikeStatus(long id, String status) throws DAOException {
        try {
            jdbcTemplate.update(SQL_CHANGE_STATUS_BIKE, status, id);
        }catch (DataAccessException e){
            LOGGER.error("Exception while changing status of bike", e);
            throw new DAOException("Exception while changing status of bike", e);
        }
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
//          Map<String,Object> values = objectMapper.convertValue(order, Map.class);
        Map<String,Object> values = new HashMap<>();
        values.put("start_date" , order.getStart_date());
        values.put("end_date", order.getEnd_date());
        values.put("user_id", order.getUser().getId());
        values.put("status", order.getStatus().name());
        values.put("sum", order.getSum());
        return orderInserter.executeAndReturnKey(values).longValue();
    }

    private void saveBikeToOrder(Bike bike, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("order_id", orderId);
        values.put("bike_id", bike.getId());
        orderBikesInserter.execute(values);
    }

    @Override
    public boolean closeOrder(Order order) throws DAOException {
        try {
            jdbcTemplate.update(SQL_CLOSE_ORDER, order.getEnd_date(), order.getStatus().name(), order.getSum(), order.getId());
            for(Bike bike : order.getBikes()) {
                jdbcTemplate.update(SQL_CLOSE_ORDER_GIVE_AWEY_BIKE, BikeStatusEnum.FREE.name(), bike.getId());
            }
            double newBalance = order.getUser().getBalance() - order.getSum();
            jdbcTemplate.update(SQL_SET_NEW_BALANCE, newBalance, order.getUser().getId());
        }catch (DataAccessException e){
            LOGGER.error("Exception was thrown during close order from DB : ", e);
            throw new DAOException("Exception was thrown during close order from DB : ", e);
        }

        return true;
    }

    @Override
    public long getOrderIdByUserAndStatus(long id, String name) throws DAOException {
        return 0;
    }

    @Override
    public Order findOpenOrder(User user) throws DAOException {
       Order order;
        try {
            order = jdbcTemplate.queryForObject(SQL_FIND_OPEN_ORDER, new Object[]{user.getId()}, new OrderMapper() );
        }catch (EmptyResultDataAccessException e){
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Exception was thrown during find open order from the DB : ", e);
            throw new DAOException("Exception was thrown during find open order from the DB : ", e);
        }
        return order;
    }


    @Override
    public int add(Order entity) throws DAOException {
        return 0;
    }

    @Override
    public Order getById(long id) throws DAOException {
        return null;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(Order entity) throws DAOException {

    }

    @Override
    public void delete(Order entity) throws DAOException {

    }
}

