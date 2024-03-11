package com.mm.app.controler;

import com.mm.app.exception.EntityExistsException;
import com.mm.app.model.User;
import com.mm.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }
    @PostMapping("/save")
    public @ResponseBody ResponseEntity<User> save(@RequestBody User user) {
        try {
//            User save = userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));

        } catch (EntityExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<List<User>> getAll(){
        return  ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
}
