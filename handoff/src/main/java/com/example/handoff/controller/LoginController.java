package com.example.handoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// ログイン画面は全ユーザがアクセス可能。
    @GetMapping("/login")
    public String login() {
        return "handoff/login"; // templates/login.html
    }
}
