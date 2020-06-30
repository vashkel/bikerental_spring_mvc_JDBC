package com.example.bikerental.mapper;

import com.example.bikerental.entity.RentalPoint;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalPointMapper implements org.springframework.jdbc.core.RowMapper<RentalPoint> {
    @Override
    public RentalPoint mapRow(ResultSet resultSet, int i) throws SQLException {
        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(resultSet.getInt("id"));
        rentalPoint.setName(resultSet.getString("name"));
        rentalPoint.setAddress(resultSet.getString("adress"));
        rentalPoint.setTel(resultSet.getString("tel"));
        return rentalPoint;
    }
}
