package com.nagarro.userservice.services;

import com.nagarro.userservice.dto.ProfileUpdateDTO;
import com.nagarro.userservice.dto.RegisterDTO;
import com.nagarro.userservice.exceptions.InvalidUserDetails;
import com.nagarro.userservice.exceptions.UserAlreadyExists;
import com.nagarro.userservice.exceptions.UserNotFoundException;
import com.nagarro.userservice.models.User;

public interface UserService {
    User registerUser(RegisterDTO registerDTO) throws InvalidUserDetails, UserAlreadyExists;

    User findUser(String id) throws UserNotFoundException;

    User updateUser(String id, ProfileUpdateDTO profileUpdateDTO) throws UserNotFoundException;
}
