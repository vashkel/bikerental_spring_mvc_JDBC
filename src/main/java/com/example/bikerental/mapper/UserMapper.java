package com.example.bikerental.mapper;

import com.example.bikerental.entity.User;
import com.example.bikerental.entity.UserStateEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
       User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setSalt(resultSet.getString("salt"));
        user.setRole(User.UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase()));
        user.setTel(resultSet.getString("tel"));
        user.setState(UserStateEnum.valueOf(resultSet.getString("state").toUpperCase()));
        user.setBalance(resultSet.getDouble("balance"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
