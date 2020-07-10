package com.example.bikerental.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BikeIdMapper implements RowMapper<Long> {
    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
       Long bikeId;
       bikeId = rs.getLong("bike_id");

        return bikeId;
    }
}
