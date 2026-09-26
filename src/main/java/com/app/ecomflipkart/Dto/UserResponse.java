package com.app.ecomflipkart.Dto;

import com.app.ecomflipkart.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserRole role;
    private AddressDTO address;

}
