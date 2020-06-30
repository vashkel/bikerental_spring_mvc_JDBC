package com.example.bikerental.dao.impl;

import com.example.bikerental.dao.UserDAO;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.DAOException;
import com.example.bikerental.mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private final static String SQL_ADD_USER = "INSERT INTO users( name, surname, login, password, salt, tel, " +
            " balance,email) VALUES (?,?,?,?,?,?,?,?)";
    private final static String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private final static String SQL_GET_ALL_USERS = "SELECT * FROM users WHERE role = 'user'";
    private final static String SQL_UPDATE_USER = "UPDATE users SET name=?,surname=?,login=?,password=?," +
            "salt =?,tel =?,balance =?,email =? WHERE id=?";
    private final static String SQL_DELETE_USER = "DELETE FROM users WHERE id=?";
    private final static String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String SQL_FIND_ID_BY_LOGIN = "SELECT id FROM users where login = ?";
    private final static String SQL_REGISTER_USER = "INSERT INTO users( name, surname, login, password, salt,email) " +
            "VALUES (?,?,?,?,?,?)";
    private final static String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private final static String SQL_CHANGE_STATE_BY_ID = "UPDATE users SET state=? WHERE id=?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_LOGIN, new UserMapper(), login);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("An exception occurred in the layer DAO while getting user by login from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting user by login from the DB", e);
        }
        return user;
    }

    @Override
    public User findByID(String id) throws DAOException {
        return null;
    }

    @Override
    public long findIdByLogin(String login) throws DAOException {
         long id;
        try {
            id = jdbcTemplate.queryForObject(SQL_FIND_ID_BY_LOGIN, new Object[]{login}, Long.class);
        }catch (EmptyResultDataAccessException e){
            return 0;
        } catch (DataAccessException e){
            LOGGER.error("An exception occurred in the layer DAO while getting id by login from the DB", e);
            throw new DAOException("An exception occurred in the layer DAO while getting id by login from the DB", e);
        }
        return id;
    }

    @Override
    public void register(User user) throws DAOException {
       jdbcTemplate.update(SQL_ADD_USER,
               user.getName(),
               user.getSurname(),
               user.getLogin(),
               user.getPassword(),
               user.getSalt(),
               user.getTel(),
               user.getBalance(),
               user.getEmail());
       }

    @Override
    public void deleteUserById(long id) throws DAOException {

    }

    @Override
    public int changeStateById(long userId, String state) throws DAOException {
        return 0;
    }

    @Override
    public void updatePassword(String password, User user) throws DAOException {

    }

    @Override
    public int add(User entity) throws DAOException {
        return jdbcTemplate.update(SQL_ADD_USER, entity.getName(),entity.getSurname(),
                entity.getLogin(),entity.getPassword(),
                entity.getSalt(),entity.getTel(),entity.getBalance(),entity.getEmail());
    }

    @Override
    public User getById(long id) throws DAOException {
       return jdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, new UserMapper(),id);
    }

    @Override
    public List<User> getAll() throws DAOException {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public void update(User entity) throws DAOException {
        jdbcTemplate.update(SQL_UPDATE_USER,entity.getName(),entity.getSurname(),
                entity.getLogin(),entity.getPassword(),
                entity.getSalt(),entity.getTel(),entity.getBalance(),entity.getEmail(),entity.getId());
    }

    @Override
    public void delete(User entity) throws DAOException {
        jdbcTemplate.update(SQL_DELETE_USER,entity.getName(),entity.getSurname(),
                entity.getLogin(),entity.getPassword(),
                entity.getSalt(),entity.getTel(),entity.getBalance(),entity.getEmail());
    }
}
