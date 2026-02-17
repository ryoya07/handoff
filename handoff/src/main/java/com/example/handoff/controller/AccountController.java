package com.example.handoff.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.handoff.repository.UserMapper;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountController(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/edit")
    public String editForm(Model model, Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        var user = userMapper.selectById(userId);

        model.addAttribute("user", user);
        return "account/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@RequestParam String displayName, Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        userMapper.updateProfile(userId, displayName);

        return "redirect:/account/edit?success";
    }
    
    @PostMapping("/password")
    public String updatePassword(
            @RequestParam String newPassword,
            @RequestParam String newPasswordConfirm,
            Authentication auth
    ) {
        if (!newPassword.equals(newPasswordConfirm)) {
            return "redirect:/account/edit?pw_mismatch";
        }

        // 最低限のチェック（必要なら強化できる）
        if (newPassword.length() < 8) {
            return "redirect:/account/edit?pw_short";
        }

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        String hash = passwordEncoder.encode(newPassword);
        userMapper.updatePasswordHash(userId, hash);

        return "redirect:/account/edit?pw_success";
    }
    
    @PostMapping("/withdraw")
    public String withdraw(Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        userMapper.softDelete(userId);

        SecurityContextHolder.clearContext();
        return "redirect:/login?deleted";
    }
}