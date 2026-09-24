package com.app.ecomflipkart.controller;

import com.app.ecomflipkart.model.User;
import com.app.ecomflipkart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getListOfUsers")
    //@RequestMapping (value = "/getListOfUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        /*
        User user = userService.getUser(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
        */
        return userService.getUser(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User created Successfully");
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        return new ResponseEntity<>(userService.updateUser(id, updatedUser), HttpStatus.OK);
    }
}
