package com.nagarro.userservice.exceptions;

public class InvalidUserDetails extends Exception {
    public InvalidUserDetails() {
        super("Invalid User Details Provided");
    }
}
