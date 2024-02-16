package com.nagarro.userservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nagarro.userservice.dto.ProfileUpdateDTO;
import com.nagarro.userservice.dto.RegisterDTO;
import com.nagarro.userservice.exceptions.InvalidUserDetails;
import com.nagarro.userservice.exceptions.UserAlreadyExists;
import com.nagarro.userservice.exceptions.UserNotFoundException;
import com.nagarro.userservice.models.User;
import com.nagarro.userservice.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/profile")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO)
            throws InvalidUserDetails, UserAlreadyExists {
        System.out.println(registerDTO);
        return new ResponseEntity<User>(this.userService.registerUser(registerDTO), HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) throws UserNotFoundException {
        return new ResponseEntity<User>(userService.findUser(id), HttpStatus.OK);
    }

    @PutMapping("profile/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id,
            @RequestBody ProfileUpdateDTO profileUpdateDTO)
            throws UserNotFoundException, InvalidUserDetails {
        return new ResponseEntity<User>(userService.updateUser(id, profileUpdateDTO), HttpStatus.OK);
    }

}
