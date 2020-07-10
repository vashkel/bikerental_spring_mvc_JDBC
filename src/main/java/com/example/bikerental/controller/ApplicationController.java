package com.example.bikerental.controller;

import com.example.bikerental.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ApplicationController {

    @GetMapping("/homePage")
    public String homePage(@SessionAttribute("user") User user) {
        return user.getRole().getHomePage();

    }
}
