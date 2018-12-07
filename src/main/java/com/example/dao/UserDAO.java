package com.example.dao;

import com.example.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> find();
    User find(long id);
    void save(User user);
    void update(User user);
    void delete(long id);
}
