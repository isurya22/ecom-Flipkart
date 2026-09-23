package com.app.ecomflipkart;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers(){
        return userList;
    }

    public Optional<User> getUser(Long id){
        /*
          for(User user : userList){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
         */
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void addUser(User user){
        user.setId(nextId++);
        userList.add(user);
    }

    public boolean updateUser(Long id, User updatedUser){
        return userList.stream()
                .filter(user-> user.getId().equals(id))
                .findFirst()
                .map(existedUser-> {
                    existedUser.setFirstName(updatedUser.getFirstName());
                    existedUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}
