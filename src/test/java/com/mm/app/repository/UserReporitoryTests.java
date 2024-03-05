package com.mm.app.repository;

import com.mm.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserReporitoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest(){
        User user = userRepository.save(new User(1, "Lana", "Radmilovic", "lana@gmail.com", "lr"));
        Assertions.assertNotNull(user);
    }

    @Test
    public void deleteTest(){
        User user = userRepository.save(new User(2, "Lena", "Peric", "lena@gamil.com", "lp"));
        userRepository.delete(user);
        Optional<User> deleted = userRepository.findById(user.getId());
        Assertions.assertFalse(deleted.isPresent());
    }

    @Test
    public void findByEmailTest() {
        User user = userRepository.save(new User(3, "Lana", "Radmilovic", "lana@gamil.com", "lr"));
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(u.isPresent());
        Assertions.assertEquals(user, u.get());
    }


}
