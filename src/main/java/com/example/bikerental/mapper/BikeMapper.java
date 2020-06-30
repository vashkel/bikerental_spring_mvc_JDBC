package com.example.bikerental.mapper;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.BikeStatusEnum;
import com.example.bikerental.entity.BikeType;
import com.example.bikerental.entity.BikeTypeEnum;
import com.example.bikerental.entity.RentalPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BikeMapper implements RowMapper<Bike> {
    @Override
    public Bike mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bike bike = new Bike();
        bike.setId(rs.getLong("bikes.id"));
        bike.setBrand(rs.getString("bikes.brand"));
        bike.setModel(rs.getString("bikes.model"));

        BikeType bikeType = new BikeType();
        bikeType.setId(rs.getLong("bikes.bike_type_id"));
        bikeType.setType(BikeTypeEnum.valueOf(rs.getString("bike_types.type").toUpperCase()));
        bike.setBikeType(bikeType);

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(rs.getLong("bikes.rental_point_id"));
        rentalPoint.setName(rs.getString("rental_points.name"));
        rentalPoint.setAddress(rs.getString("rental_points.adress"));
        rentalPoint.setTel(rs.getString("rental_points.tel"));
        bike.setRentalPoint(rentalPoint);

        bike.setBikeStatus(BikeStatusEnum.valueOf(rs.getString("bikes.status").toUpperCase()));

        return bike;
    }
}
