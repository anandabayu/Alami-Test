package com.anandabayu.alami.dao;

import com.anandabayu.alami.entity.User;

import java.util.List;

public interface UserDAO {

    public List<User> findAll();

    public User findById(int id);

    public void create(User user);
}
