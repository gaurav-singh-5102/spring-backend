package com.nagarro.userservice.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nagarro.userservice.dto.ProfileUpdateDTO;
import com.nagarro.userservice.dto.RegisterDTO;
import com.nagarro.userservice.exceptions.InvalidUserDetails;
import com.nagarro.userservice.exceptions.UserAlreadyExists;
import com.nagarro.userservice.exceptions.UserNotFoundException;
import com.nagarro.userservice.models.User;
import com.nagarro.userservice.repository.UserRepository;
import com.nagarro.userservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private Validator validator;
    private UserRepository userRepository;

    public UserServiceImpl(Validator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(RegisterDTO registerDTO) throws InvalidUserDetails, UserAlreadyExists {
        validateUser(registerDTO);
        if (userAlreadyExists(registerDTO)) {
            throw new UserAlreadyExists();
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setImage(registerDTO.getImage());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return userRepository.save(user);
    }

    private boolean userAlreadyExists(RegisterDTO registerDTO) {
        return userRepository.existsByEmail(registerDTO.getEmail());
    }

    private void validateUser(RegisterDTO registerDTO) throws InvalidUserDetails {
        Errors errors = new BeanPropertyBindingResult(registerDTO, "entity");
        validator.validate(registerDTO, errors);
        if (errors.hasErrors()) {
            throw new InvalidUserDetails();
        }
    }

    public User findUser(String id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    @Override
    public User updateUser(String id, ProfileUpdateDTO profileUpdateDTO) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        User existingUser = user.get();
        existingUser.setName(profileUpdateDTO.getName());
        existingUser.setImage(profileUpdateDTO.getImage());
        return userRepository.save(existingUser);
    }

}
