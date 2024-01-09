package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.User;


public interface IUserServices {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password,Long roleId) throws Exception;
}
