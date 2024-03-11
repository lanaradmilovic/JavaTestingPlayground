package com.mm.app.service.impl;

import com.mm.app.exception.EntityExistsException;
import com.mm.app.exception.InvalidEntityException;
import com.mm.app.model.User;
import com.mm.app.repository.UserRepository;
import com.mm.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) throws EntityExistsException {
        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if (result.isPresent()) {
            throw new EntityExistsException("already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws InvalidEntityException {
        Optional<User> result = userRepository.findById(id);
        if (result.isEmpty()) {
            throw new InvalidEntityException("user doesn't exists");
        }
        userRepository.deleteById(id);
    }
}
