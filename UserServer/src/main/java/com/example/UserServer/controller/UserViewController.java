package com.example.UserServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
	@GetMapping("/signin-user")
    public String signInPage() {
        return "signin"; 
    }
    
    @GetMapping("/signup-user")
    public String signUpPage() {
        return "signup";
    }
    
    @GetMapping("/checkout-user")
    public String checkOutPage() {
    	return "checkout";
    }
}

