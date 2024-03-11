package com.mm.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.app.controler.UserController;
import com.mm.app.exception.EntityExistsException;
import com.mm.app.model.User;
import com.mm.app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @MockBean
    private UserService userService;

    // komponenta kojom okidamo neku od metoda
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveUserSuccessTest() throws Exception{
        User user = new User(1, "UC1", "Uc1", "uc1@gmail.com", "pass");
        when(userService.save(user)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(user.getId())))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.password", equalTo(user.getPassword())));
verify(userService, times(1)).save(user);


    }
@Test
    public void saveUserFailureTest() throws Exception{
        User user = new User(1l, "UC1", "Uc1", "uc1@gmail.com", "pass");
        when(userService.save(user)).thenThrow(EntityExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());



    }

    @Test
    public void findAllTest() throws Exception {
        User user1 = new User(3l, "UC1", "Uc1", "uc1@gmail.com", "pass");
        User user2 = new User(4l, "UC1", "Uc1", "uc1@gmail.com", "pass");
        List<User> users = Arrays.asList(user1, user2);
        when(userService.findAll()).thenReturn(users);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/all")).andExpect(status().isOk()).andReturn();
        List<User> returnedUsers = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {

        });
        Assertions.assertTrue(returnedUsers.contains(user1));
        Assertions.assertTrue(returnedUsers.contains(user2));

    }
}
