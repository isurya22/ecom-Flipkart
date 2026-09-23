package com.app.ecomflipkart;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers(){
        return userList;
    }

    public User getUser(Long id){
        for(User user : userList){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public void addUser(User user){
        user.setId(nextId++);
        userList.add(user);
    }
}
