package com.app.ecomflipkart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/getListOfUsers")
    public List<User> getAllUsers(){
        return userService.fetchAllUsers();
    }

    @GetMapping("/api/getUser/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping("/api/createUser")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "User created Successfully";
    }
}
