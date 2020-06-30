package com.example.bikerental.mapper;

import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.OrderStatusEnum;
import com.example.bikerental.entity.User;
import com.example.bikerental.entity.UserStateEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        try {
            order.setId(resultSet.getLong("o.id"));
            order.setStart_date(resultSet.getTimestamp("o.start_date").toLocalDateTime());
            if(resultSet.getTimestamp("o.end_date")!= null) {
                order.setEnd_date(resultSet.getTimestamp("o.end_date").toLocalDateTime());
            }
            User user = new User();
            user.setId(resultSet.getLong("u.id"));
            user.setName(resultSet.getString("u.name"));
            user.setSurname(resultSet.getString("u.surname"));
            user.setLogin(resultSet.getString("u.login"));
            user.setPassword(resultSet.getString("u.password"));
            user.setRole(User.UserRoleEnum.valueOf(resultSet.getString("u.role").toUpperCase()));
            user.setTel(resultSet.getString("u.tel"));
            user.setState(UserStateEnum.valueOf(resultSet.getString("u.state").toUpperCase()));
            user.setBalance(resultSet.getDouble("u.balance"));

            order.setUser(user);

//            Bike bike = new Bike();
//            bike.setId(resultSet.getLong("bk.id"));
//            bike.setBrand(resultSet.getString("bk.brand"));
//            bike.setModel(resultSet.getString("bk.model"));
//
//            BikeType bikeType = new BikeType();
//            bikeType.setId(resultSet.getLong("bt.id"));
//            bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("bt.type").toUpperCase()));
//
//            bike.setBikeType(bikeType);
//
//            RentalPoint rentalPoint = new RentalPoint();
//            rentalPoint.setId(resultSet.getLong("rp.id"));
//            rentalPoint.setName(resultSet.getString("rp.name"));
//            rentalPoint.setAddress(resultSet.getString("rp.adress"));
//            rentalPoint.setTel(resultSet.getString("rp.tel"));
//            bike.setRentalPoint(rentalPoint);
//
//            order.setBike(bike);

            order.setStatus(OrderStatusEnum.valueOf(resultSet.getString("o.status").toUpperCase()));
            order.setSum(resultSet.getDouble("o.sum"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
