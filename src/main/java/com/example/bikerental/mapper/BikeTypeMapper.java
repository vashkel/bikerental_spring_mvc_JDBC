package com.example.bikerental.mapper;

import com.example.bikerental.entity.BikeType;
import com.example.bikerental.entity.BikeTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BikeTypeMapper implements RowMapper<BikeType> {
    @Override
    public BikeType mapRow(ResultSet resultSet, int i) throws SQLException {
        BikeType bikeType = new BikeType();
        bikeType.setId(resultSet.getInt("id"));
        bikeType.setType(BikeTypeEnum.valueOf(resultSet.getString("type").toUpperCase()));
        return bikeType;
    }
}
