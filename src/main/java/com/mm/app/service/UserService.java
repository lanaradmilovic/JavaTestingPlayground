package com.mm.app.service;

import com.mm.app.exception.InvalidEntityException;
import com.mm.app.model.User;
import jakarta.persistence.EntityExistsException;

import java.util.List;

public interface UserService {
    User save(User user) throws EntityExistsException, com.mm.app.exception.EntityExistsException;
    List<User> findAll();
    void deleteById(Long id) throws InvalidEntityException;

}
