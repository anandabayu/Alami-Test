package com.anandabayu.alami.service;

import com.anandabayu.alami.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(int id);

    public void create(User user);
}
