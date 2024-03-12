package com.mm.app.service;

import com.mm.app.exception.EntityExistsException;
import com.mm.app.exception.InvalidEntityException;
import com.mm.app.model.User;
import com.mm.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void saveSuccessTest() throws EntityExistsException{
        User user = new User(1L, "Lana", "Radmilovic", "lana@gmail.com", "pass");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        User u = userService.save(user);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(user, u);
    }

    @Test
    public void saveFailureTest() {
        User user = new User(1L, "Lana", "Radmilovic", "lana@gmail.com", "pass");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityExistsException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    public void deleteSuccessTest() throws InvalidEntityException {
        User user = new User(1L, "Lana", "Radmilovic", "lana@gmail.com", "pass");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());


    }
    @Test
    public void deleteFailureTest(){
        User user = new User(1L, "Lana", "Radmilovic", "lana@gmail.com", "pass");
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidEntityException.class, () -> {
            userService.deleteById(user.getId());
        });
    }

//    @Test
//    public void findAllTset() {
//        User user1 = new User(5l, "us5", "us 5", "us@gmail.com", "us5");
//        User user2 = new User(6l, "us6", "us 5", "us@gmail.com", "us5");
//        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
//    }

}
