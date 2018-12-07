package com.example.dao.impl;

import com.example.dao.UserDAO;
import com.example.dao.mapper.UserRowMapper;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String BASE_QUERY = "SELECT id, name, age FROM users ";
    private static final String INSERT_QUERY = "INSERT INTO users (name, age) VALUES (?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET name= ?, age = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> find() {
        return jdbcTemplate.query(
                BASE_QUERY,
                new UserRowMapper()
        );
    }

    @Override
    public User find(long id) {
        try {

            Object[] params = {id};
            return jdbcTemplate.queryForObject(
                    BASE_QUERY.concat("WHERE id = ?"),
                    params,
                    new UserRowMapper()
            );

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        Object[] params = {
                user.getName(),
                user.getAge(),
        };
        jdbcTemplate.update(INSERT_QUERY, params);
    }

    @Override
    public void update(User user) {
        Object[] params = {
                user.getName(),
                user.getAge(),
                user.getId()
        };
        jdbcTemplate.update(UPDATE_QUERY, params);
    }

    @Override
    public void delete(long id) {
        Object[] params = {id};
        jdbcTemplate.update(DELETE_QUERY, params);
    }
}