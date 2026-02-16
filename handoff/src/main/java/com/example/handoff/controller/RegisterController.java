package com.example.handoff.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.handoff.repository.UserMapper;

@Controller
public class RegisterController {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/register")
    public String showForm() {
        return "handoff/register";
    }

    @PostMapping("/register")
    public String register(
        @RequestParam String loginId,
        @RequestParam String displayName,
        @RequestParam String password,
        Model model
    ) {

        String passwordHash = passwordEncoder.encode(password);

        userMapper.insertUser(loginId, displayName, passwordHash);

        return "redirect:/login";
    }
}
