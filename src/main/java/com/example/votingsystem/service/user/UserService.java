package com.example.votingsystem.service.user;


import com.example.votingsystem.model.User;
import com.example.votingsystem.to.UserTo;
import com.example.votingsystem.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();
}