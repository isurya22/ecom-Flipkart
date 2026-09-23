package com.app.ecomflipkart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/getListOfUsers")
    public List<User> getAllUsers(){
        return userService.fetchAllUsers();
    }

    @PostMapping("/api/createUser")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "User created Successfully";
    }
}
