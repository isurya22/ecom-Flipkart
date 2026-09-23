package com.app.ecomflipkart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    List<User> userList = new ArrayList<>();

    @GetMapping("/api/getListOfUsers")
    public List<User> getAllUsers(){
        return userList;
    }

    @PostMapping("/api/createUser")
    public List<User> createUser(@RequestBody User user){
        userList.add(user);
        return userList;
    }
}
